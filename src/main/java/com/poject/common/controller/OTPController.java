//package com.poject.common.controller;
//
//import com.poject.common.model.dto.CommonResponse;
//import com.poject.common.model.dto.OTPResponse;
//import com.poject.common.model.entity.CommonOTPEntity;
//import com.poject.common.service.OTPService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/common/otp")
//public class OTPController {
//
//    private final OTPService otpService;
//
//    public OTPController(OTPService otpService) {
//        this.otpService = otpService;
//    }
//
//    @PostMapping("/generate/{length}/{send}")
//    public ResponseEntity<OTPResponse> generateOtp(@PathVariable(value = "length", required = false) Integer length,
//                                                   @PathVariable(value = "send", required = false) String send,
//                                                   @RequestBody CommonOTPEntity commonOtpEntity) {
//        return otpService.generateOtp(length, send, commonOtpEntity);
//    }
//
//    @GetMapping("/status/{id}")
//    public ResponseEntity<CommonResponse> statusOTP(
//            @PathVariable(value = "id", required = false) Long id) {
//        return otpService.statusOtp(id);
//    }
//
//    @GetMapping("/verify/{module}/{phone}/{password}")
//    public ResponseEntity<Object> verifyOTP(@PathVariable(value = "module", required = false) String module,
//                                            @PathVariable(value = "phone", required = false) String phone,
//                                            @PathVariable(value = "password", required = false) String password) {
//        return otpService.verifyOtp(module, phone, password);
//    }
//
//}
