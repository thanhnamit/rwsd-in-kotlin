package com.tna.exception

import java.lang.RuntimeException

class CSVSyntaxException(override val message: String): RuntimeException(message)