package com.sopt.saver.saver.Electronics;

import java.util.ArrayList;

/**
 * Created by kyi42 on 2017-07-03.
 */

public class ETCDetailResult {
    public ResultData result;
    public String message;
    public class ResultData
    {
        public ETCProductData info;
        public ArrayList<ESellerData> comment;
    }
}
