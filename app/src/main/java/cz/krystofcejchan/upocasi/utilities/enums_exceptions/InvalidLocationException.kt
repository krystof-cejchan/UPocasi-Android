package cz.krystofcejchan.upocasi.utilities.enums_exceptions

class InvalidLocationException : RuntimeException {
    constructor(s: String?) : super(s) {}
    constructor() {}
}