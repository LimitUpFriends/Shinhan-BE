//package com.LimitUpFriends.shinhan.config;
//
//import com.LimitUpFriends.shinhan.dto.CommunityDto;
//import com.LimitUpFriends.shinhan.service.CommunityService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/v1/{stock_id}/community")
//public class CommunityController {
//    private CommunityService communityService;
//
//    public CommunityController(CommunityService communityService) {
//        this.communityService = communityService;
//    }
//
//
//    @PostMapping
//    public ResponseEntity<String> writing(@PathVariable("stock_id") int stock_id, @RequestBody CommunityDto dto) {
//        try {
//            communityService.writing(stock_id, dto);
//            return ResponseEntity.status(201).body("글이 등록되었습니다.");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("글 등록 실패 \n" + e.getMessage());
//        }
//    }
//
//
//    @PostMapping
//    public ResponseEntity<String> searching(@PathVariable("stock_id") int stock_id, @RequestBody CommunityDto dto) {
//        try {
//            communityService.searching(stock_id, dto);
//            return ResponseEntity.ok().build();
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("글 조회 실패 \n" + e.getMessage());
//        }
//    }
//
//
//    @PostMapping
//    public ResponseEntity<String> deleting(@PathVariable("stock_id") int stock_id, @RequestBody CommunityDto dto) {
//        try {
//            communityService.writing(stock_id, dto);
//            return ResponseEntity.status(201).body("해당 글이 삭제되었습니다.");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("글 삭제 실패 \n" + e.getMessage());
//        }
//    }
//}
//
//
//
