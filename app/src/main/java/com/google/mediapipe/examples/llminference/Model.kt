package com.google.mediapipe.examples.llminference
const val t1 = "https://static-cse.canva.com/blob/1182335/tools_feature_webp-to-jpg_how-to2x.jpg"
const val t2 = "https://storage.googleapis.com/kaggle-models-data/104600/124284/bundle/archive.tar.gz?GoogleAccessId=web-data@kaggle-161607.iam.gserviceaccount.com&Expires=1740646854&Signature=c1eHWijxwYwFrk9EfBHKhuQ9PgBJxBYV4o3fkChGF5eRR6nm2xZywoWV5NMRQFxwmTTTp8Yxss6C9W0xEta6IpQgg1SaF0EtVSzFcLG3mXT0ieO8ojhqEO7vas7vXyuDyQ43Iet9CpFfu61n2T8Reu7yh%2Fu7NDGBUEGQV3CDBIJLUytJHcJ0%2FoU1D%2BLN8xl99B5JvF%2Bk9KEb8eF2DVVpOEJsZ5JIloS%2FK42gKtf4Eu6CDDhRs1ZSwucuyh8beR3svKIDbBKwF7yaHEuJ4znJuBOP1jrg6105hy9hX9uFrJXDb%2B%2B8Us0AWZ7T0f1fn7UY%2FqPutAg2jEyzt6hiW4JIVA%3D%3D&response-content-disposition=attachment%3B+filename%3Dgemma-2-tflite-gemma2-2b-it-cpu-int8-v1.tar.gz"
const val t3 = "https://d1apbb4wrgy3cq.cloudfront.net/gemma-2b-it-cpu-int4.bin"
// NB: Make sure the filename is *unique* per model you use!
// Weight caching is currently based on filename alone.
enum class Model(val path: String, val url: String, val uiState: UiState, val temperature: Float, val topK: Int, val topP: Float) {
    MODEL_LOAD("/data/local/tmp/llm/gemma-2b-it-cpu-int4.bin", t3, GemmaUiState(), temperature = 0.8f, topK = 40, topP = 1.0f),
//    DEEPSEEK_CPU("/data/local/tmp/llm/deepseek3k_q8_ekv1280.task","", DeepSeeUiState(), temperature = 0.6f, topK = 40, topP = 0.7f),
}
