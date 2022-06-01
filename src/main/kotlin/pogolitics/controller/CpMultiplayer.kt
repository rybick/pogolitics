package pogolitics.controller

object CpMultiplayer {
    private val map: Map<Float, Double> = mapOf(
        1F    to 0.094,
        1.5F  to 0.1351374318,
        2F    to 0.16639787,
        2.5F  to 0.192650919,
        3F    to 0.21573247,
        3.5F  to 0.2365726613,
        4F    to 0.25572005,
        4.5F  to 0.2735303812,
        5F    to 0.29024988,
        5.5F  to 0.3060573775,
        6F    to 0.3210876,
        6.5F  to 0.3354450362,
        7F    to 0.34921268,
        7.5F  to 0.3624577511,
        8F    to 0.3752356,
        8.5F  to 0.387592416,
        9F    to 0.39956728,
        9.5F  to 0.4111935514,
        10F   to 0.4225,
        10.5F to 0.4329264091,
        11F   to 0.44310755,
        11.5F to 0.4530599591,
        12F   to 0.4627984,
        12.5F to 0.472336093,
        13F   to 0.48168495,
        13.5F to 0.4908558003,
        14F   to 0.49985844,
        14.5F to 0.508701765,
        15F   to 0.51739395,
        15.5F to 0.5259425113,
        16F   to 0.5343543,
        16.5F to 0.5426357375,
        17F   to 0.5507927,
        17.5F to 0.5588305862,
        18F   to 0.5667545,
        18.5F to 0.5745691333,
        19F   to 0.5822789,
        19.5F to 0.5898879072,
        20F   to 0.5974,
        20.5F to 0.6048236651,
        21F   to 0.6121573,
        21.5F to 0.6194041216,
        22F   to 0.6265671,
        22.5F to 0.6336491432,
        23F   to 0.64065295,
        23.5F to 0.6475809666,
        24F   to 0.65443563,
        24.5F to 0.6612192524,
        25F   to 0.667934,
        25.5F to 0.6745818959,
        26F   to 0.6811649,
        26.5F to 0.6876849038,
        27F   to 0.69414365,
        27.5F to 0.70054287,
        28F   to 0.7068842,
        28.5F to 0.7131691091,
        29F   to 0.7193991,
        29.5F to 0.7255756136,
        30F   to 0.7317,
        30.5F to 0.7347410093,
        31F   to 0.7377695,
        31.5F to 0.7407855938,
        32F   to 0.74378943,
        32.5F to 0.7467812109,
        33F   to 0.74976104,
        33.5F to 0.7527290867,
        34F   to 0.7556855,
        34.5F to 0.7586303683,
        35F   to 0.76156384,
        35.5F to 0.7644860647,
        36F   to 0.76739717,
        36.5F to 0.7702972656,
        37F   to 0.7731865,
        37.5F to 0.7760649616,
        38F   to 0.77893275,
        38.5F to 0.7817900548,
        39F   to 0.784637,
        39.5F to 0.7874736075,
        40F   to 0.7903,
        40.5F to 0.792803968,
        41F   to 0.79530001,
        41.5F to 0.797800015,
        42F   to 0.8003,
        42.5F to 0.802799995,
        43F   to 0.8053,
        43.5F to 0.8078,
        44F   to 0.81029999,
        44.5F to 0.812799985,
        45F   to 0.81529999,
        45.5F to 0.81779999,
        46F   to 0.82029999,
        46.5F to 0.82279999,
        47F   to 0.82529999,
        47.5F to 0.82779999,
        48F   to 0.83029999,
        48.5F to 0.83279999,
        49F   to 0.83529999,
        49.5F to 0.83779999,
        50F   to 0.84029999,
        50.5F to 0.84279999,
        51F   to 0.84529999
    )

    operator fun get(level: Float): Double {
        return map[level] ?: throw IndexOutOfBoundsException("Level must be in [1, 51] range and be a multiple of 0.5")
    }
}

fun calcStatValue(baseStat: Int, iv: Int = 15, level: Float = 40F): Double {
    return CpMultiplayer[level] * (baseStat + iv)
}