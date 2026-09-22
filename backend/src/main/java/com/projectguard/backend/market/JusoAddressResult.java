package com.projectguard.backend.market;

/**
 * @param roadAddr 도로명주소
 * @param admCd    10자리 법정동코드. 앞 5자리가 실거래가 API의 LAWD_CD(시군구코드)다.
 * @param bdNm     건물명(공동주택명)
 */
public record JusoAddressResult(String roadAddr, String admCd, String bdNm) {

    public String lawdCd() {
        return admCd != null && admCd.length() >= 5 ? admCd.substring(0, 5) : null;
    }
}
