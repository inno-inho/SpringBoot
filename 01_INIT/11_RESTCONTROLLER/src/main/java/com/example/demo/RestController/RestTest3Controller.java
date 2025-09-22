package com.example.demo.RestController;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/items")
@Slf4j
// CORS(백엔드서버와 프론트엔드의 서바가 다르면 접속이 안되는 문제) 해결하기 위한 것
@CrossOrigin(
//        origins = {"http://192.168.5.50:5500", "http://localhost:5500"},
        originPatterns = "*",   // 모든 클라이언트 혀용
        allowCredentials = "false",
        allowedHeaders = {"Content-Type", "apllication/json"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}

)
public class RestTest3Controller {
    // 메모리 저장소(테스트용)
    private final Map<Long, ItemResponse> store = new ConcurrentHashMap<>();
    private long seq = 1L;

    // GET /api/items?q=keyword  (목록 조회 + 간단 검색)
    @GetMapping
    public ResponseEntity<List<ItemResponse>> list(@RequestParam(required = false) String q) {
        List<ItemResponse> all = new ArrayList<>(store.values());
        if (q != null && !q.isBlank()) {
            all.removeIf(i -> !i.getName().toLowerCase().contains(q.toLowerCase()));
        }
        return ResponseEntity.ok(all);
    }

    // GET /api/items/{id} (단건 조회)
    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> get(@PathVariable Long id) {
        ItemResponse found = store.get(id);
        if (found == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(found);
    }

    // POST /api/items (생성)
    @PostMapping
    public ResponseEntity<ItemResponse> create(@RequestBody ItemRequest req) {
        ItemResponse saved = ItemResponse.builder()
                .id(seq++)
                .name(req.getName())
                .price(req.getPrice())
                .build();
        store.put(saved.getId(), saved);
        return ResponseEntity.ok(saved);
    }


    // PUT /api/items/{id} (전체 수정)
    @PutMapping("/{id}")
    public ResponseEntity<ItemResponse> update(@PathVariable Long id, @RequestBody ItemRequest req) {
        ItemResponse found = store.get(id);
        if (found == null) return ResponseEntity.notFound().build();
        ItemResponse updated = ItemResponse.builder()
                .id(id)
                .name(req.getName())
                .price(req.getPrice())
                .build();
        store.put(id, updated);
        return ResponseEntity.ok(updated);
    }

    // PATCH /api/items/{id} (부분 수정)
    @PatchMapping("/{id}")
    public ResponseEntity<ItemResponse> patch(@PathVariable Long id, @RequestBody Map<String, Object> patch) {
        ItemResponse found = store.get(id);
        if (found == null) return ResponseEntity.notFound().build();

        String name = found.getName();
        Integer price = found.getPrice();

        if (patch.containsKey("name") && patch.get("name") != null) {
            name = String.valueOf(patch.get("name"));
        }
        if (patch.containsKey("price") && patch.get("price") != null) {
            price = (Integer) (patch.get("price") instanceof Number ? ((Number) patch.get("price")).intValue() : null);
        }

        ItemResponse updated = ItemResponse.builder()
                .id(id)
                .name(name)
                .price(price)
                .build();
        store.put(id, updated);
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/items/{id} (삭제)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ItemResponse removed = store.remove(id);
        if (removed == null) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    // DTO
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemRequest {
        private String name;
        private Integer price;
    }

    @Data
    @Builder
    public static class ItemResponse {
        private Long id;
        private String name;
        private Integer price;
    }
}