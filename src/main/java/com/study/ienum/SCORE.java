package com.study.ienum;

import java.util.Arrays;
import java.util.Enumeration;

/**
 * @author ：fei
 * @date ：Created in 2019/10/11 0011 11:09
 */
public enum SCORE implements IEnum<SCORE> {
    DI(50,"低分"),ZHONG(70,"中分"),GAO(90,"高分");
    SCORE(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
    Integer code;
    String msg;

    @Override
    public Integer getCode() {
        return code;
    }


    public static SCORE getEnumByCode(Integer code) {
        return Arrays.stream(SCORE.values())
                .filter(score -> score.code.equals(code))
                .findFirst().get();
    }

    public void setCode(Integer code) {
        this.code = code;
    }


}
