// Copyright (c) 2003-present, Jodd Team (http://jodd.org)
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
// 1. Redistributions of source code must retain the above copyright notice,
// this list of conditions and the following disclaimer.
//
// 2. Redistributions in binary form must reproduce the above copyright
// notice, this list of conditions and the following disclaimer in the
// documentation and/or other materials provided with the distribution.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
// POSSIBILITY OF SUCH DAMAGE.

package jodd.system;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Host information.
 */
abstract class HostInfo {

	/**
	 * Delegate host info to be resolved lazy.
	 * Android detection will initialize this class too and since {@code InetAddress.getLocalHost()}
	 * is forbidden in Android, we will get an exception.
	 */
	private static class HostInfoLazy {
		private final String HOST_NAME;
		private final String HOST_ADDRESS;

		public HostInfoLazy() {
			String hostName;
			String hostAddress;

			try {
				final InetAddress localhost = InetAddress.getLocalHost();

				hostName = localhost.getHostName();
				hostAddress = localhost.getHostAddress();
			} catch (final UnknownHostException uhex) {
				hostName = "localhost";
				hostAddress = "127.0.0.1";
			}

			this.HOST_NAME = hostName;
			this.HOST_ADDRESS = hostAddress;
		}
	}

	private static HostInfoLazy hostInfoLazy;

	/**
	 * Returns host name.
	 */
	public final String getHostName() {
		if (hostInfoLazy == null) {
			hostInfoLazy = new HostInfoLazy();
		}
		return hostInfoLazy.HOST_NAME;
	}

	/**
	 * Returns host IP address.
	 */
	public final String getHostAddress() {
		if (hostInfoLazy == null) {
			hostInfoLazy = new HostInfoLazy();
		}
		return hostInfoLazy.HOST_ADDRESS;
	}

	// ---------------------------------------------------------------- util

	protected String nosep(final String in) {
		if (in.endsWith(File.separator)) {
			return in.substring(0, in.length() - 1);
		}
		return in;
	}
}
