package com.pcms.util;

public interface PcmsConst {
    /**
     * 生成文件存放地址
     */
    //String FILEPATH = "/usr/local/website/model";
    String FILEPATH = "//Users/feng/html";
    String EXCELPATH = "/Users/feng/excel";

    String RESPCODE = "respCode";

    String RESPMSD = "respMsd";

    String url = "http://www.nitethoughts.club/model/";

    String REQUESTTYPE_NOTFOUNDMOIVE="findMovie";

    String REQUESTTYPE_MOIVELINKNOTUSE="linkINvalid";

    interface  RequestMoive{

      String  STATUS_INIT="0";

      String  STATUS_HASHANDLE="1";

    }
}
