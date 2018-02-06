package com.sopt.saver.saver.Electronics;

import java.util.ArrayList;

/**
 * Created by kyi42 on 2017-07-02.
 */

public class EDetailResult {
    public ResultData result;
    public String message;
    public class ResultData
    {
        public EProductData info;
        public ArrayList<ESellerData> comment;
    }
}
