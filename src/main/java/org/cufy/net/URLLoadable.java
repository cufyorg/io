/*
 * Copyright (c) 2019, LSafer, All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * -You can edit this file (except the header).
 * -If you have change anything in this file. You
 *  shall mention that this file has been edited.
 *  By adding a new header (at the bottom of this header)
 *  with the word "Editor" on top of it.
 */
package org.cufy.net;

import cufy.io.BufferedReader;
import cufy.io.*;
import cufy.lang.Instructor;
import org.cufy.lang.PathImplLoadable;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

/**
 * A loadable that uses {@link java.net.URL} as it's container.
 *
 * @author LSaferSE
 * @version 3 release (16-Feb-2020)
 * @since 15-Feb-2020
 */
public interface URLLoadable extends PathImplLoadable<URL> {
	@Override
	default InputStream getInputStream() throws IOException {
		URLConnection connection = this.getPath().openConnection();
		connection.setDoInput(true);
		connection.setDoOutput(false);

		InputStream base = connection.getInputStream();
		InputStream buff = new cufy.io.BufferedInputStream(base);

		return buff;
	}
	@Override
	default InputStream getInputStream(Instructor instructor) throws IOException {
		Objects.requireNonNull(instructor, "instructor");

		URLConnection connection = this.getPath().openConnection();
		connection.setDoInput(true);
		connection.setDoOutput(false);

		InputStream base = connection.getInputStream();
		InputStream buff = new cufy.io.BufferedInputStream(base);
		InputStream ctrl = new RemoteInputStream(instructor, buff);

		return ctrl;
	}

	@Override
	default OutputStream getOutputStream() throws IOException {
		URLConnection connection = this.getPath().openConnection();
		connection.setDoInput(false);
		connection.setDoOutput(true);

		OutputStream base = connection.getOutputStream();

		return base;
	}
	@Override
	default OutputStream getOutputStream(Instructor instructor) throws IOException {
		Objects.requireNonNull(instructor, "instructor");

		URLConnection connection = this.getPath().openConnection();
		connection.setDoInput(false);
		connection.setDoOutput(true);

		OutputStream base = connection.getOutputStream();
		OutputStream ctrl = new RemoteOutputStream(instructor, base);

		return ctrl;
	}

	@Override
	default Reader getReader() throws IOException {
		URLConnection connection = this.getPath().openConnection();
		connection.setDoInput(true);
		connection.setDoOutput(false);

		InputStream base = connection.getInputStream();

		Reader conv = new InputStreamReader(base);
		Reader buff = new cufy.io.BufferedReader(conv);

		return buff;
	}
	@Override
	default Reader getReader(Instructor instructor) throws IOException {
		Objects.requireNonNull(instructor, "instructor");

		URLConnection connection = this.getPath().openConnection();
		connection.setDoInput(true);
		connection.setDoOutput(false);

		InputStream base = connection.getInputStream();

		Reader conv = new InputStreamReader(base);
		Reader buff = new BufferedReader(conv);
		Reader ctrl = new RemoteReader(instructor, buff);

		return ctrl;
	}

	@Override
	default Writer getWriter() throws IOException {
		URLConnection connection = this.getPath().openConnection();
		connection.setDoInput(false);
		connection.setDoOutput(true);

		OutputStream base = connection.getOutputStream();

		Writer conv = new OutputStreamWriter(base);

		return conv;
	}
	@Override
	default Writer getWriter(Instructor instructor) throws IOException {
		Objects.requireNonNull(instructor, "instructor");

		URLConnection connection = this.getPath().openConnection();
		connection.setDoInput(false);
		connection.setDoOutput(true);

		OutputStream base = connection.getOutputStream();

		Writer conv = new OutputStreamWriter(base);
		Writer ctrl = new RemoteWriter(instructor, conv);

		return ctrl;
	}
}