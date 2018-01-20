/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package construction_project;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;


/**
 *
 * @author Mohammad.y
 */
public class FileSequenceReader {
       /**
	 * Returns the data from the next sub-file in the given file sequence
	 * stream.
	 * <p>
	 * If no sub-files remain, returns null. If the stream ends prematurely,
	 * throws an EOFException.
	 */
	public static byte[] readOneFile(InputStream sequence) throws IOException,
			EOFException {
		// sequence files consist of a (4-byte) int giving the size of the
		// sub-file,
		// followed by the sub-file, followed by another size, followed by the
		// sub-file,
		// and so on until EOF

		ByteArrayOutputStream dest = new ByteArrayOutputStream();
		byte[] bufSizeOfFile = new byte[4];
		int read = 0;

		read = sequence.read(bufSizeOfFile);
		dest.write(bufSizeOfFile, 0, 4);

		ByteBuffer bb = ByteBuffer.wrap(bufSizeOfFile);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		int size = bb.getInt();
		System.out.println("sss "+size);
		
		
		byte[] fileBuffer = new byte[size];
		while(size > 0){
			int nRead = sequence.read(
            		fileBuffer, 0, size);

            size -= nRead;
		}
		
		
		
		return fileBuffer;
	}
}
