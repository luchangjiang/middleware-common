package com.lotstock.eddid.common.core.exception;

import com.lotstock.eddid.common.core.util.R;

public class ResultException extends RuntimeException {

    private R resultModel;

    public ResultException(R resultModel) {
        super(resultModel.getMsg());
        this.resultModel = resultModel;
    }

    public ResultException(Throwable cause, R resultModel) {
        super(cause);
        this.resultModel = resultModel;
//        this.resultModel.setResult(cause);
    }

    public R getResultModel() {
        return resultModel;
    }




}
