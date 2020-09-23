# HiAscii Charset Provider

This project is a `java.nio.charset.Charset` implementation that
maps between Java character codepoints and a variant of ASCII, with the upper
eighth bit set, that was used by the Apple II range of computers.

It's packaged as an add-on module for the Ghidra Software Reverse Engineering
Framework since it is intended for use with the Charset String Analyzer add-on
module.

The contents of `src\main` could be extracted, compiled and packaged as a
JAR. At that point the HiAscii character set provder will be available with a
call to `Charset.forName("HI-ASCII")`.

