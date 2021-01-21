package com.advanced.c10annotation.enumbox;

import lombok.Getter;

public enum CountryEnum {
    ONE(1,"齐"),TWO(2,"楚"),THREE(3,"燕"),FOUR(4,"赵"),FIVE(5,"魏"),SIX(6,"韩");
    @Getter
    private Integer retCode;
    @Getter private String retMsg;

    CountryEnum(Integer retCode, String retMsg) {
        this.retCode = retCode;
        this.retMsg = retMsg;
    }
    public static CountryEnum get_CountryEnum(int index) {
//        CountryEnum[] values = CountryEnum.values();
        for (CountryEnum element : CountryEnum.values()) {
            if (index == element.getRetCode()) {
                return element;
            }
        }
        return null;
    }
}
