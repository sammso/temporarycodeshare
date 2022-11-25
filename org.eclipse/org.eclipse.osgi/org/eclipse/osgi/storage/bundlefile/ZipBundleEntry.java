/*******************************************************************************
 * Copyright (c) 2005, 2018 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Rob Harrop - SpringSource Inc. (bug 253942)
 *******************************************************************************/
package org.eclipse.osgi.storage.bundlefile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.ZipEntry;

/**
 * A BundleEntry represented by a ZipEntry in a ZipFile.  The ZipBundleEntry
 * class is used for bundles that are installed as a ZipFile on a file system.
 */
public class ZipBundleEntry extends BundleEntry {
	/**
	 * ZipEntry for this entry.
	 */
	protected final ZipEntry zipEntry;

	/**
	 * The BundleFile for this entry.
	 */
	protected final ZipBundleFile bundleFile;

	/**
	 * Constructs the BundleEntry using a ZipEntry.
	 * @param bundleFile BundleFile object this entry is a member of
	 * @param zipEntry ZipEntry object of this entry
	 */
	ZipBundleEntry(ZipEntry zipEntry, ZipBundleFile bundleFile) {
		this.zipEntry = zipEntry;
		this.bundleFile = bundleFile;
	}

	/**
	 * Return an InputStream for the entry.
	 *
	 * @return InputStream for the entry
	 * @exception java.io.IOException
	 */
	public InputStream getInputStream() throws IOException {
		return bundleFile.getInputStream(zipEntry);
	}

	/**
	 * Return size of the uncompressed entry.
	 *
	 * @return size of entry
	 */
	public long getSize() {
		return zipEntry.getSize();
	}

	/**
	 * Return name of the entry.
	 *
	 * @return name of entry
	 */
	public String getName() {
		return zipEntry.getName();
	}

	/**
	 * Get the modification time for this BundleEntry.
	 * <p>If the modification time has not been set,
	 * this method will return <tt>-1</tt>.
	 *
	 * @return last modification time.
	 */
	public long getTime() {
		return zipEntry.getTime();
	}

	@SuppressWarnings("deprecation")
	public URL getLocalURL() {
		try {
			return new URL("jar:" + bundleFile.basefile.toURL() + "!/" + zipEntry.getName()); //$NON-NLS-1$//$NON-NLS-2$
		} catch (MalformedURLException e) {
			//This can not happen. 
			return null;
		}
	}

	@SuppressWarnings("deprecation")
	public URL getFileURL() {
		try {
			File file = bundleFile.getFile(zipEntry.getName(), false);
			if (file != null)
				return file.toURL();
		} catch (MalformedURLException e) {
			//This can not happen. 
		}
		return null;
	}
}
