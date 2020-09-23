/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nz.gen.coffee.ghidra.hiascii;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;


public class HiAscii extends Charset {

	public static final String NAME = "HI-ASCII";
	
	public static final Charset INSTANCE = new HiAscii();

	public HiAscii() {
		super(NAME, null);
	}

	@Override
	public boolean contains(Charset cs) {
		return cs instanceof HiAscii;
	}

	@Override
	public CharsetDecoder newDecoder() {
		return new Decoder(this);
	}

	@Override
	public CharsetEncoder newEncoder() {
		return new Encoder(this);
	}

	private static class Encoder extends CharsetEncoder {

		public Encoder(final Charset charset) {
			super(charset, 1.0f, 1.0f, new byte[] {(byte) 0xBf});
		}

		@Override
		protected CoderResult encodeLoop(final CharBuffer in, final ByteBuffer out) {
			while (in.hasRemaining()) {
				char character = in.charAt(0);
				if (character > 127) {
					return inputTermination(character, in);
				}
				if (!out.hasRemaining()) {
					return CoderResult.OVERFLOW;
				}
				out.put((byte)(character | 0x80));
				in.position(in.position()+1);
			}
			return CoderResult.UNDERFLOW;
		}

		private CoderResult inputTermination(char character, final CharBuffer in) {
			if (Character.isLowSurrogate(character)) {
				return CoderResult.malformedForLength(1);
			}
			if (Character.isHighSurrogate(character)) {
				if (in.remaining() == 1) {
					return CoderResult.UNDERFLOW;
				}
				if (Character.isLowSurrogate(in.charAt(1))) {
					return CoderResult.unmappableForLength(2);
				}
				return CoderResult.malformedForLength(1);
			}
			return CoderResult.unmappableForLength(1);
		}
		
		@Override
		public boolean canEncode(char c) {
            return c < 0x80;
		}
		
	}
	
	private static class Decoder extends CharsetDecoder {
		public Decoder(final Charset charset) {
			super(charset, 1.0f, 1.0f);
		}

		@Override
		protected CoderResult decodeLoop(final ByteBuffer in, final CharBuffer out) {
			int mark = in.position();
			try {
                while (in.hasRemaining()) {
                    int encoded = in.get() & 0xFF;
                    if (encoded >= 0x80 && encoded <= 0xFF) {
                        if (!out.hasRemaining()) {
                            return CoderResult.OVERFLOW;
                        }
                        out.put((char)(encoded & 0x7F));
                        mark++;
                        continue;
                    }
                    return CoderResult.malformedForLength(1);
                }
                return CoderResult.UNDERFLOW;
			} finally {
				in.position(mark);
			}
		}
				
	}
}
