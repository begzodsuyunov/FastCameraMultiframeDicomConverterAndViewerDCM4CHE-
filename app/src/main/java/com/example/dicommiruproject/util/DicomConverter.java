package com.example.dicommiruproject.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.ElementDictionary;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.UID;
import org.dcm4che3.data.VR;
import org.dcm4che3.io.DicomOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class DicomConverter {

    private static final ElementDictionary DICT = ElementDictionary.getStandardElementDictionary();

    private static final int[] TYPE2_TAGS = {
            Tag.ContentDate,
            Tag.ContentTime
    };

    // Default values for patient information
    private static String patientName = "John";
    private static String patientId = "123123";
    private static String patientSex = "M";
    private static String patientAge = "23";
    private static String patientDob = "19500101";
    private static String patientAddress = "Tashkent";
    private static String institutionName = "Miru";
    private static String manufacturer = "Manu-Miru";
    private static String manufacturerModelName = "Man-Model-Miru";
    private static String referringPhysicianName = "Dr Employee";
    private static String studyDate = "20100101";
    private static String studyDescription = "Study desc";
    private static String studyID = "123123";
    private static String seriesDate = "20100101";


    public static String getPatientName() {
        return patientName;
    }

    public static void setPatientName(String patientName) {
        DicomConverter.patientName = patientName;
    }

    public static String getPatientId() {
        return patientId;
    }

    public static void setPatientId(String patientId) {
        DicomConverter.patientId = patientId;
    }

    public static String getPatientSex() {
        return patientSex;
    }

    public static void setPatientSex(String patientSex) {
        DicomConverter.patientSex = patientSex;
    }
    public static String getPatientAge() {
        return patientAge;
    }

    public static void setPatientAge(String patientAge) {
        DicomConverter.patientAge = patientAge;
    }
    public static String getPatientBirthDate() {
        return patientDob;
    }

    public static void setPatientBirthDate(String patientDob) {
        DicomConverter.patientDob = patientDob;
    }
    public static String getPatientAddress() {
        return patientAddress;
    }

    public static void setPatientAddress(String patientAddress) {
        DicomConverter.patientAddress = patientAddress;
    }
    public static String getInstitutionName() {
        return institutionName;
    }

    public static void setInstitutionName(String institutionName) {
        DicomConverter.institutionName = institutionName;
    }
    public static String getManufacturer() {
        return manufacturer;
    }

    public static void setManufacturer(String manufacturer) {
        DicomConverter.manufacturer = manufacturer;
    }
    public static String getManufacturerModelName() {
        return manufacturerModelName;
    }

    public static void setManufacturerModelName(String manufacturerModelName) {
        DicomConverter.manufacturerModelName = manufacturerModelName;
    }
    public static String getReferringPhysicianName() {
        return referringPhysicianName;
    }

    public static void setReferringPhysicianName(String referringPhysicianName) {
        DicomConverter.referringPhysicianName = referringPhysicianName;
    }
    public static String getStudyDate() {
        return studyDate;
    }

    public static void setStudyDate(String studyDate) {
        DicomConverter.studyDate = studyDate;
    }
    public static String getStudyDescription() {
        return studyDescription;
    }

    public static void setStudyDescription(String studyDescription) {
        DicomConverter.studyDescription = studyDescription;
    }
    public static String getStudyID() {
        return studyID;
    }

    public static void setStudyID(String studyID) {
        DicomConverter.studyID = studyID;
    }

    public static String getSeriesDate() {
        return seriesDate;
    }

    public static void setSeriesDate(String seriesDate) {
        DicomConverter.seriesDate = seriesDate;
    }

    public static void createMultiFrameDicom(String inputFolder, String outputFilePath) {
        // Validate input folder
        File inputDir = new File(inputFolder);
        if (!inputDir.exists() || !inputDir.isDirectory()) {
            throw new RuntimeException("Directory not found: " + inputFolder);
        }

        // Get the current time
        long currentTimeMillis = System.currentTimeMillis();

//        List<String> jpegFiles = new ArrayList<>();
//        for (File file : inputDir.listFiles()) {
//            if (file.isFile() && file.getName().toLowerCase().endsWith(".jpg")
//                    && currentTimeMillis - file.lastModified() <= 5000) {
//                jpegFiles.add(file.getAbsolutePath());
//            }
//        }
        List<String> jpegFiles = new ArrayList<>();
        for (File file : inputDir.listFiles()) {
            if (file.isFile() && file.getName().toLowerCase().endsWith(".jpg")) {
                jpegFiles.add(file.getAbsolutePath());
            }
        }
        // Create a new multi-frame DICOM dataset
        Attributes attrs = new Attributes();

        // Add study, series, and SOP instance UIDs
        attrs.setString(Tag.StudyInstanceUID, VR.UI, "1.2.276.0.7230010.3.1.4.1782478493.9132.1704159839.406");
        attrs.setString(Tag.SeriesInstanceUID, VR.UI, "1.2.276.0.7230010.3.1.4.1782478493.9132.1704159839.406.1");
        attrs.setString(Tag.SOPInstanceUID, VR.UI, "1.2.276.0.7230010.3.1.4.1782478493.9132.1704159839.406.1");

        // Set Transfer Syntax UID
//        attrs.setString(Tag.TransferSyntaxUID, VR.UI, UID.ExplicitVRLittleEndian);

        // Referring Physician's Name
        attrs.setString(Tag.ReferringPhysicianName, VR.PN, "referringPhysicianName");

        // Patient information
        attrs.setString(Tag.PatientName, VR.PN, patientName);
        attrs.setString(Tag.PatientID, VR.LO, patientId);
        attrs.setString(Tag.PatientSex, VR.CS, patientSex);
        attrs.setString(Tag.PatientAge, VR.AS, patientAge);
        attrs.setString(Tag.PatientBirthDate, VR.DA, patientDob);
        attrs.setString(Tag.PatientAddress, VR.ST, patientAddress);

        // Study information
        attrs.setString(Tag.StudyDate, VR.DA, studyDate);
        attrs.setString(Tag.StudyDescription, VR.LO, studyDescription);
        attrs.setString(Tag.StudyID, VR.SH, studyID);

        // Additional patient information
        attrs.setString(Tag.InstitutionName, VR.LO, institutionName);
        attrs.setString(Tag.Manufacturer, VR.LO, manufacturer);
        attrs.setString(Tag.ManufacturerModelName, VR.LO, manufacturerModelName);
        attrs.setString(Tag.ReferringPhysicianName, VR.PN, referringPhysicianName);

        // Series information
        attrs.setString(Tag.SeriesDate, VR.DA, seriesDate);
        attrs.setString(Tag.SOPClassUID, VR.UI, "1.2.840.10008.5.1.4.1.1.7");

        List<byte[]> jpegBytesList = new ArrayList<>();
        for (String jpegFile : jpegFiles) {
            try {
                File file = new File(jpegFile);
                byte[] jpegBytes = new byte[(int) file.length()];
                FileInputStream fis = new FileInputStream(file);
                fis.read(jpegBytes);
                fis.close();
                jpegBytesList.add(jpegBytes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        List<Bitmap> bitmapList = new ArrayList<>();
        for (String jpegFile : jpegFiles) {
            try {
                File file = new File(jpegFile);
                Bitmap originalBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

                // Rotate the bitmap by 90 degrees
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                Bitmap rotatedBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, originalBitmap.getWidth(), originalBitmap.getHeight(), matrix, true);

                bitmapList.add(rotatedBitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        if (!bitmapList.isEmpty()) {
            // Assuming all bitmaps have the same dimensions
            int width = bitmapList.get(0).getWidth();
            int height = bitmapList.get(0).getHeight();

            // Set Rows and Columns based on the shape of the first frame
            attrs.setInt(Tag.Rows, VR.US, height);
            attrs.setInt(Tag.Columns, VR.US, width);

            // Set the number of frames
            attrs.setInt(Tag.NumberOfFrames, VR.IS, bitmapList.size());

            // Additional attributes related to pixel data
            attrs.setInt(Tag.BitsAllocated, VR.US, 8);
            attrs.setInt(Tag.BitsStored, VR.US, 8);
            attrs.setInt(Tag.HighBit, VR.US, 7);
            attrs.setInt(Tag.PixelRepresentation, VR.US, 0);

            // For RGB images
            attrs.setInt(Tag.SamplesPerPixel, VR.US, 3);
            attrs.setString(Tag.PhotometricInterpretation, VR.CS, "RGB");

            // Set the Planar Configuration attribute to 0 (chunky)
            attrs.setInt(Tag.PlanarConfiguration, VR.US, 0);

            ByteBuffer encapsulatedPixelData = ByteBuffer.allocate(bitmapList.size() * width * height * 3);

            for (Bitmap bitmap : bitmapList) {
                int[] pixels = new int[width * height];
                bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

                for (int pixel : pixels) {
                    // Extract RGB components and write to the buffer
                    encapsulatedPixelData.put((byte) ((pixel >> 16) & 0xFF)); // Red
                    encapsulatedPixelData.put((byte) ((pixel >> 8) & 0xFF));  // Green
                    encapsulatedPixelData.put((byte) (pixel & 0xFF));         // Blue
                }
            }

            // Adjust the buffer position and limit accordingly
            encapsulatedPixelData.flip();


            // Set the encapsulated pixel data
            attrs.setBytes(Tag.PixelData, VR.OB, encapsulatedPixelData.array());
        } else {
            // Handle the case where bitmapList is empty (no JPEG files found)
            System.out.println("No JPEG files found in the specified folder.");
        }
        long timestamp = System.currentTimeMillis();

        // Add a timestamp to the output file name
        File outputFile = new File(outputFilePath + File.separator + "multiframe_" + timestamp + ".dcm");

        try (DicomOutputStream dos = new DicomOutputStream(outputFile)) {
            dos.writeDataset(attrs.createFileMetaInformation(UID.ExplicitVRLittleEndian), attrs);
            System.out.println("File saved successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
