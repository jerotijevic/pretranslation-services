package com.company.companymtservice.constants;

public class Constants {

    private Constants(){}
    /**
     * CRON constants
     */
    public static final String CRON_EVERY_MINUTE = "0 * * * * *";
    public static final String CRON_EVERY_TEN_MINUTES = "0 */10 * * * *";
    public static final String CRON_DAILY_AT_MIDNIGHT = "0 0 0 * * *";

    /**
     * Validator limits constants
     */
    public static final Integer MAX_CONTENT_WORD_SIZE = 30;

    /**
     * Validator parameters
     */
    public static final String PARAM_CONTENT = "Content";
    public static final String PARAM_DOMAIN = "Domain";
    public static final String PARAM_SOURCE_LANG = "Source language";
    public static final String PARAM_TARGET_LANG = "Target language";

    /**
     * Validator messages
     */
    public static final String VAL_NOT_SUPPORTED = " is not supported at the moment.";
    public static final String VAL_SEND_PARAM = " must be sent.";
    public static final String VAL_MAX_WORD_MSG = "Maximum number of words exceeded.";

    /**
     * Services messages
     */
    public static final String LANG_CODES_GET = "Getting language code list from: ";
    public static final String LANG_CODES_REC = "Language codes received: ";
    public static final String NOT_AVAILABLE = " is not available.";
    public static final String DOMAINS_GET = "Getting available domain list from: ";
    public static final String DOMAINS_REC = "Domains received: ";

    /**
     * Controller messages
     */
    public static final String REQ_RECEIVED = "Received request for validation.";
    public static final String REQ_VALIDATED = "Request validated successfully. Sending to translation.";
}
