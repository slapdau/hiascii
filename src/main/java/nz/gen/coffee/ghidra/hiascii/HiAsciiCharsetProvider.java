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

import java.nio.charset.Charset;
import java.nio.charset.spi.CharsetProvider;
import java.util.Arrays;
import java.util.Iterator;

public class HiAsciiCharsetProvider extends CharsetProvider {

	@Override
	public Iterator<Charset> charsets() {
		return Arrays.asList(HiAscii.INSTANCE).iterator();
	}

	@Override
	public Charset charsetForName(final String charsetName) {
		if (HiAscii.NAME.contentEquals(charsetName)) {
			return HiAscii.INSTANCE;
		}
		return null;
	}

}
