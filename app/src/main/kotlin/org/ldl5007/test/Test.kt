package org.ldl5007.test

import org.ldl5007.cstructure.annotation.CStruct
import org.ldl5007.cstructure.annotation.CStructField

@CStruct
class TestStruct {

    @CStructField
    var def: Int = 2

    @CStructField
    var abc: Int = 1
}