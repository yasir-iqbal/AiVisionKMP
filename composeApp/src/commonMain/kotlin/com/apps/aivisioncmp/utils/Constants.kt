package com.apps.aivisioncmp.utils


object Constants {
    const val BASE_URL = "https://api.openai.com/v1/"
    const val WEB_CLIENT_ID = "185864616892-u27mn6ift3137827g8jog0tjgj89v594.apps.googleusercontent.com"
    const val REWARDED_AD_UNIT_ID =
        "ca-app-pub-3940256099942544/5224354917" //ca-app-pub-3940256099942544/6300978111
    const val BANNER_AD_UNIT_ID = "ca-app-pub-3940256099942544/6300978111"
    const val BANNER_AD_UNIT_ID2 = "ca-app-pub-3940256099942544/6300978111"
    const val INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712"
    const val PRIVACY_POLICY = "https://www.google.com/"
    const val TERMS_SERVICE = "https://www.google.com/"
    const val GPT_KEY = "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"
    const val DAILY_FREE_CREDITS=5
    const val CHAT_MESSAGE_COST = 1
    const val CHAT_MESSAGE_GPT4_COST = 2
    const val BASE_IMAGE_GEN_COST = 2
    const val MESSAGES_WORDS_TURBO = 500
    const val MESSAGES_WORDS_GPT4 = 150

    const val SUBSCRIPTION_PRODUCT_ID = "aivision_sub"
    const val WEEKLY_PLAN_ID = "aivision-sub-weekly"
    const val MONTHLY_PLAN_ID = "aivision-sub-monthly"
    const val YEARLY_PLAN_ID = "aivision-sub-yearly"

   /* const val VISION_API_KEY ="" // Required for guest mode only and if imageInput mode is Astica
    const val GPT_API_KEY =""  // Required for guest mode only
    const val STABILITY_API_KEY =""  // Required for guest mode only*/
    val SignInMode = SignInType.Both
    //val VisionPlatform = VisionGenerationType.OPENAI  // astica is cheaper then opnai but results of opnenai are better
    //val ImageGenerationPlatform = GenerationModel.STABILITY // stability is much cheaper then Dall-e-3
    const val IS_IMAGE_INPUT_PAID = false

    const val EMAIL_DOMAIN = "@inclogics.co"
    const val ENABLED_PDF_FEATURE = true
    const val MAX_PDF_PAGES_PER_FILE = 10

    // Premium plan daily usage limit
    const val MAX_VISION_LIMIT_PER_DAY =50
    const val MAX_IMAGE_GEN_LIMIT_PER_DAY=50
    const val MAX_GPT4_MESSAGE_LIMIT_PER_DAY=50
}