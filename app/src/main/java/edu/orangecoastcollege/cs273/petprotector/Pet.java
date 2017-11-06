package edu.orangecoastcollege.cs273.petprotector;

/**
 * The <code>Pet</code> class maintains information about a pet,
 * including its id number, name, details, phone number and image name.
 */
public class Pet {

    private long mId;
    private String mName;
    private String mDetails;
    private String mPhoneNumber;
    private String mImageUri;

    /**
     * Creates a new <code>Pet</code> from its name, details, phoneNumber, imageUri.
     * @param id The id number
     * @param name The pet name
     * @param details The details about the pet
     * @param phoneNumber The phone number
     * @param imageUri The URI of the pet image
     */
    public Pet(int id, String name, String details, String phoneNumber, String imageUri) {
        mId = id;
        mName = name;
        mDetails = details;
        mPhoneNumber = phoneNumber;
        mImageUri = imageUri;
    }

    /**
     * Creates a new <code>Pet</code> from its name, details, phoneNumber, imageUri.
     * @param name The pet name
     * @param details The details about the pet
     * @param phoneNumber The phone number
     * @param imageUri The URI of the pet image
     */
    public Pet(String name, String details, String phoneNumber, String imageUri) {
        mId = -1;
        mName = name;
        mDetails = details;
        mPhoneNumber = phoneNumber;
        mImageUri = imageUri;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDetails() {
        return mDetails;
    }

    public void setDetails(String details) {
        mDetails = details;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getImageUri() {
        return mImageUri;
    }

    public void setImageUri(String imageUri) {
        mImageUri = imageUri;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mDetails='" + mDetails + '\'' +
                ", mPhoneNumber='" + mPhoneNumber + '\'' +
                ", mImageUri='" + mImageUri + '\'' +
                '}';
    }
}
