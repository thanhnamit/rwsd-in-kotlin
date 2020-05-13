package com.tna.exception

import java.lang.RuntimeException

class CSVLineFormatException(override val message: String): RuntimeException(message)