; Listing generated by Microsoft (R) Optimizing Compiler Version 19.13.26128.0 

	TITLE	C:\FER\VI\OOUP\lab\1\visual cpp\fourth\fourth\fourth.cpp
	.686P
	.XMM
	include listing.inc
	.model	flat

INCLUDELIB MSVCRTD
INCLUDELIB OLDNAMES

PUBLIC	?set@PlainOldClass@@QAEXH@Z			; PlainOldClass::set
PUBLIC	??0Base@@QAE@XZ					; Base::Base
PUBLIC	?set@CoolClass@@UAEXH@Z				; CoolClass::set
PUBLIC	?get@CoolClass@@UAEHXZ				; CoolClass::get
PUBLIC	??0CoolClass@@QAE@XZ				; CoolClass::CoolClass
PUBLIC	_main
PUBLIC	??_7Base@@6B@					; Base::`vftable'
PUBLIC	??_7CoolClass@@6B@				; CoolClass::`vftable'
PUBLIC	??_R4Base@@6B@					; Base::`RTTI Complete Object Locator'
PUBLIC	??_R0?AVBase@@@8				; Base `RTTI Type Descriptor'
PUBLIC	??_R3Base@@8					; Base::`RTTI Class Hierarchy Descriptor'
PUBLIC	??_R2Base@@8					; Base::`RTTI Base Class Array'
PUBLIC	??_R1A@?0A@EA@Base@@8				; Base::`RTTI Base Class Descriptor at (0,-1,0,64)'
PUBLIC	??_R4CoolClass@@6B@				; CoolClass::`RTTI Complete Object Locator'
PUBLIC	??_R0?AVCoolClass@@@8				; CoolClass `RTTI Type Descriptor'
PUBLIC	??_R3CoolClass@@8				; CoolClass::`RTTI Class Hierarchy Descriptor'
PUBLIC	??_R2CoolClass@@8				; CoolClass::`RTTI Base Class Array'
PUBLIC	??_R1A@?0A@EA@CoolClass@@8			; CoolClass::`RTTI Base Class Descriptor at (0,-1,0,64)'
EXTRN	__purecall:PROC
EXTRN	??2@YAPAXI@Z:PROC				; operator new
EXTRN	@_RTC_CheckStackVars@8:PROC
EXTRN	@__security_check_cookie@4:PROC
EXTRN	__RTC_CheckEsp:PROC
EXTRN	__RTC_InitBase:PROC
EXTRN	__RTC_Shutdown:PROC
EXTRN	??_7type_info@@6B@:QWORD			; type_info::`vftable'
EXTRN	___security_cookie:DWORD
;	COMDAT rtc$TMZ
rtc$TMZ	SEGMENT
__RTC_Shutdown.rtc$TMZ DD FLAT:__RTC_Shutdown
rtc$TMZ	ENDS
;	COMDAT rtc$IMZ
rtc$IMZ	SEGMENT
__RTC_InitBase.rtc$IMZ DD FLAT:__RTC_InitBase
rtc$IMZ	ENDS
;	COMDAT ??_R1A@?0A@EA@CoolClass@@8
rdata$r	SEGMENT
??_R1A@?0A@EA@CoolClass@@8 DD FLAT:??_R0?AVCoolClass@@@8 ; CoolClass::`RTTI Base Class Descriptor at (0,-1,0,64)'
	DD	01H
	DD	00H
	DD	0ffffffffH
	DD	00H
	DD	040H
	DD	FLAT:??_R3CoolClass@@8
rdata$r	ENDS
;	COMDAT ??_R2CoolClass@@8
rdata$r	SEGMENT
??_R2CoolClass@@8 DD FLAT:??_R1A@?0A@EA@CoolClass@@8	; CoolClass::`RTTI Base Class Array'
	DD	FLAT:??_R1A@?0A@EA@Base@@8
rdata$r	ENDS
;	COMDAT ??_R3CoolClass@@8
rdata$r	SEGMENT
??_R3CoolClass@@8 DD 00H				; CoolClass::`RTTI Class Hierarchy Descriptor'
	DD	00H
	DD	02H
	DD	FLAT:??_R2CoolClass@@8
rdata$r	ENDS
;	COMDAT ??_R0?AVCoolClass@@@8
data$r	SEGMENT
??_R0?AVCoolClass@@@8 DD FLAT:??_7type_info@@6B@	; CoolClass `RTTI Type Descriptor'
	DD	00H
	DB	'.?AVCoolClass@@', 00H
data$r	ENDS
;	COMDAT ??_R4CoolClass@@6B@
rdata$r	SEGMENT
??_R4CoolClass@@6B@ DD 00H				; CoolClass::`RTTI Complete Object Locator'
	DD	00H
	DD	00H
	DD	FLAT:??_R0?AVCoolClass@@@8
	DD	FLAT:??_R3CoolClass@@8
rdata$r	ENDS
;	COMDAT ??_R1A@?0A@EA@Base@@8
rdata$r	SEGMENT
??_R1A@?0A@EA@Base@@8 DD FLAT:??_R0?AVBase@@@8		; Base::`RTTI Base Class Descriptor at (0,-1,0,64)'
	DD	00H
	DD	00H
	DD	0ffffffffH
	DD	00H
	DD	040H
	DD	FLAT:??_R3Base@@8
rdata$r	ENDS
;	COMDAT ??_R2Base@@8
rdata$r	SEGMENT
??_R2Base@@8 DD	FLAT:??_R1A@?0A@EA@Base@@8		; Base::`RTTI Base Class Array'
rdata$r	ENDS
;	COMDAT ??_R3Base@@8
rdata$r	SEGMENT
??_R3Base@@8 DD	00H					; Base::`RTTI Class Hierarchy Descriptor'
	DD	00H
	DD	01H
	DD	FLAT:??_R2Base@@8
rdata$r	ENDS
;	COMDAT ??_R0?AVBase@@@8
data$r	SEGMENT
??_R0?AVBase@@@8 DD FLAT:??_7type_info@@6B@		; Base `RTTI Type Descriptor'
	DD	00H
	DB	'.?AVBase@@', 00H
data$r	ENDS
;	COMDAT ??_R4Base@@6B@
rdata$r	SEGMENT
??_R4Base@@6B@ DD 00H					; Base::`RTTI Complete Object Locator'
	DD	00H
	DD	00H
	DD	FLAT:??_R0?AVBase@@@8
	DD	FLAT:??_R3Base@@8
rdata$r	ENDS
;	COMDAT ??_7CoolClass@@6B@
CONST	SEGMENT
??_7CoolClass@@6B@ DD FLAT:??_R4CoolClass@@6B@		; CoolClass::`vftable'
	DD	FLAT:?set@CoolClass@@UAEXH@Z
	DD	FLAT:?get@CoolClass@@UAEHXZ
CONST	ENDS
;	COMDAT ??_7Base@@6B@
CONST	SEGMENT
??_7Base@@6B@ DD FLAT:??_R4Base@@6B@			; Base::`vftable'
	DD	FLAT:__purecall
	DD	FLAT:__purecall
CONST	ENDS
; Function compile flags: /Odtp /RTCsu /ZI
; File c:\fer\vi\ooup\lab\1\visual cpp\fourth\fourth\fourth.cpp
;	COMDAT _main
_TEXT	SEGMENT
tv75 = -236						; size = 4
$T1 = -228						; size = 4
_pb$ = -24						; size = 4
_poc$ = -12						; size = 4
__$ArrayPad$ = -4					; size = 4
_main	PROC						; COMDAT

	push	ebp
	mov	ebp, esp
	sub	esp, 236				; 000000ecH
	push	ebx
	push	esi
	push	edi
	lea	edi, DWORD PTR [ebp-236]
	mov	ecx, 59					; 0000003bH
	mov	eax, -858993460				; ccccccccH
	rep stosd
	mov	eax, DWORD PTR ___security_cookie
	xor	eax, ebp
	mov	DWORD PTR __$ArrayPad$[ebp], eax

	push	8
	call	??2@YAPAXI@Z				; operator new
	add	esp, 4
	mov	DWORD PTR $T1[ebp], eax
	cmp	DWORD PTR $T1[ebp], 0
	je	SHORT $LN3@main
	mov	ecx, DWORD PTR $T1[ebp]
	call	??0CoolClass@@QAE@XZ
	mov	DWORD PTR tv75[ebp], eax
	jmp	SHORT $LN4@main
$LN3@main:
	mov	DWORD PTR tv75[ebp], 0
$LN4@main:
	mov	eax, DWORD PTR tv75[ebp]
	mov	DWORD PTR _pb$[ebp], eax

	push	42					; 0000002aH
	lea	ecx, DWORD PTR _poc$[ebp]
	call	?set@PlainOldClass@@QAEXH@Z		; PlainOldClass::set

	mov	esi, esp
	push	42					; 0000002aH
	mov	eax, DWORD PTR _pb$[ebp]
	mov	edx, DWORD PTR [eax]
	mov	ecx, DWORD PTR _pb$[ebp]
	mov	eax, DWORD PTR [edx]
	call	eax
	cmp	esi, esp
	call	__RTC_CheckEsp

	xor	eax, eax
	push	edx
	mov	ecx, ebp
	push	eax
	lea	edx, DWORD PTR $LN7@main
	call	@_RTC_CheckStackVars@8
	pop	eax
	pop	edx
	pop	edi
	pop	esi
	pop	ebx
	mov	ecx, DWORD PTR __$ArrayPad$[ebp]
	xor	ecx, ebp
	call	@__security_check_cookie@4
	add	esp, 236				; 000000ecH
	cmp	ebp, esp
	call	__RTC_CheckEsp
	mov	esp, ebp
	pop	ebp
	ret	0
	npad	3
$LN7@main:
	DD	1
	DD	$LN6@main
$LN6@main:
	DD	-12					; fffffff4H
	DD	4
	DD	$LN5@main
$LN5@main:
	DB	112					; 00000070H
	DB	111					; 0000006fH
	DB	99					; 00000063H
	DB	0
_main	ENDP
_TEXT	ENDS
; Function compile flags: /Odtp /RTCsu /ZI
;	COMDAT ??0CoolClass@@QAE@XZ
_TEXT	SEGMENT
_this$ = -8						; size = 4
??0CoolClass@@QAE@XZ PROC				; CoolClass::CoolClass, COMDAT
; _this$ = ecx
	push	ebp
	mov	ebp, esp
	sub	esp, 204				; 000000ccH
	push	ebx
	push	esi
	push	edi
	push	ecx
	lea	edi, DWORD PTR [ebp-204]
	mov	ecx, 51					; 00000033H
	mov	eax, -858993460				; ccccccccH
	rep stosd
	pop	ecx
	mov	DWORD PTR _this$[ebp], ecx
	mov	ecx, DWORD PTR _this$[ebp]
	call	??0Base@@QAE@XZ
	mov	eax, DWORD PTR _this$[ebp]
	mov	DWORD PTR [eax], OFFSET ??_7CoolClass@@6B@
	mov	eax, DWORD PTR _this$[ebp]
	pop	edi
	pop	esi
	pop	ebx
	add	esp, 204				; 000000ccH
	cmp	ebp, esp
	call	__RTC_CheckEsp
	mov	esp, ebp
	pop	ebp
	ret	0
??0CoolClass@@QAE@XZ ENDP				; CoolClass::CoolClass
_TEXT	ENDS
; Function compile flags: /Odtp /RTCsu /ZI
; File c:\fer\vi\ooup\lab\1\visual cpp\fourth\fourth\fourth.cpp
;	COMDAT ?get@CoolClass@@UAEHXZ
_TEXT	SEGMENT
_this$ = -8						; size = 4
?get@CoolClass@@UAEHXZ PROC				; CoolClass::get, COMDAT
; _this$ = ecx

	push	ebp
	mov	ebp, esp
	sub	esp, 204				; 000000ccH
	push	ebx
	push	esi
	push	edi
	push	ecx
	lea	edi, DWORD PTR [ebp-204]
	mov	ecx, 51					; 00000033H
	mov	eax, -858993460				; ccccccccH
	rep stosd
	pop	ecx
	mov	DWORD PTR _this$[ebp], ecx
	mov	eax, DWORD PTR _this$[ebp]
	mov	eax, DWORD PTR [eax+4]
	pop	edi
	pop	esi
	pop	ebx
	mov	esp, ebp
	pop	ebp
	ret	0
?get@CoolClass@@UAEHXZ ENDP				; CoolClass::get
_TEXT	ENDS
; Function compile flags: /Odtp /RTCsu /ZI
; File c:\fer\vi\ooup\lab\1\visual cpp\fourth\fourth\fourth.cpp
;	COMDAT ?set@CoolClass@@UAEXH@Z
_TEXT	SEGMENT
_this$ = -8						; size = 4
_x$ = 8							; size = 4
?set@CoolClass@@UAEXH@Z PROC				; CoolClass::set, COMDAT
; _this$ = ecx

	push	ebp
	mov	ebp, esp
	sub	esp, 204				; 000000ccH
	push	ebx
	push	esi
	push	edi
	push	ecx
	lea	edi, DWORD PTR [ebp-204]
	mov	ecx, 51					; 00000033H
	mov	eax, -858993460				; ccccccccH
	rep stosd
	pop	ecx
	mov	DWORD PTR _this$[ebp], ecx
	mov	eax, DWORD PTR _this$[ebp]
	mov	ecx, DWORD PTR _x$[ebp]
	mov	DWORD PTR [eax+4], ecx
	pop	edi
	pop	esi
	pop	ebx
	mov	esp, ebp
	pop	ebp
	ret	4
?set@CoolClass@@UAEXH@Z ENDP				; CoolClass::set
_TEXT	ENDS
; Function compile flags: /Odtp /RTCsu /ZI
;	COMDAT ??0Base@@QAE@XZ
_TEXT	SEGMENT
_this$ = -8						; size = 4
??0Base@@QAE@XZ PROC					; Base::Base, COMDAT
; _this$ = ecx
	push	ebp
	mov	ebp, esp
	sub	esp, 204				; 000000ccH
	push	ebx
	push	esi
	push	edi
	push	ecx
	lea	edi, DWORD PTR [ebp-204]
	mov	ecx, 51					; 00000033H
	mov	eax, -858993460				; ccccccccH
	rep stosd
	pop	ecx
	mov	DWORD PTR _this$[ebp], ecx
	mov	eax, DWORD PTR _this$[ebp]
	mov	DWORD PTR [eax], OFFSET ??_7Base@@6B@
	mov	eax, DWORD PTR _this$[ebp]
	pop	edi
	pop	esi
	pop	ebx
	mov	esp, ebp
	pop	ebp
	ret	0
??0Base@@QAE@XZ ENDP					; Base::Base
_TEXT	ENDS
; Function compile flags: /Odtp /RTCsu /ZI
; File c:\fer\vi\ooup\lab\1\visual cpp\fourth\fourth\fourth.cpp
;	COMDAT ?set@PlainOldClass@@QAEXH@Z
_TEXT	SEGMENT
_this$ = -8						; size = 4
_x$ = 8							; size = 4
?set@PlainOldClass@@QAEXH@Z PROC			; PlainOldClass::set, COMDAT
; _this$ = ecx

	push	ebp
	mov	ebp, esp
	sub	esp, 204				; 000000ccH
	push	ebx
	push	esi
	push	edi
	push	ecx
	lea	edi, DWORD PTR [ebp-204]
	mov	ecx, 51					; 00000033H
	mov	eax, -858993460				; ccccccccH
	rep stosd
	pop	ecx
	mov	DWORD PTR _this$[ebp], ecx
	mov	eax, DWORD PTR _this$[ebp]
	mov	ecx, DWORD PTR _x$[ebp]
	mov	DWORD PTR [eax], ecx
	pop	edi
	pop	esi
	pop	ebx
	mov	esp, ebp
	pop	ebp
	ret	4
?set@PlainOldClass@@QAEXH@Z ENDP			; PlainOldClass::set
_TEXT	ENDS
END