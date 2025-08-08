package com.LimitUpFriends.shinhan.controller.login;

import com.LimitUpFriends.shinhan.dto.JoinRequestDTO;
import com.LimitUpFriends.shinhan.service.login.JoinService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

    /**
     * 회원가입
     * POST api/v1/join
     * @param joinRequestDto
     * @return
     */
    @PostMapping("/auth/join")
    public ResponseEntity<String> join(@RequestBody JoinRequestDTO joinRequestDto) {
        try{
            joinService.joinProcess(joinRequestDto);
            return ResponseEntity.status(201).body("Join Successful");
        }catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    /**
     * 아이디 중복 확인
     * GET api/v1/auth/check-memberId?memberId={}
     * @param memberId
     * @return
     */
    @GetMapping("/auth/check-memberId")
    public ResponseEntity<Boolean> checkMemberId(@RequestParam("memberId") String memberId) {
        try{
            Boolean isAvailable= joinService.memberIdAvailable(memberId);
            return  ResponseEntity.status(200).body(isAvailable);
        }catch (Exception e){
            return ResponseEntity.status(500).body(false);
        }
    }
}
