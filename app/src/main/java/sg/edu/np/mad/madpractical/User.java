package sg.edu.np.mad.madpractical;
import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private boolean followed;
    private String msg;

    private int ID;
    private String desc;

    public User() {
    }

    public User(int id, String msg, String desc,boolean followed) {
        this.ID=id;
        this.followed = followed;
        this.msg = msg;
        this.desc = desc;
    }

    protected User(Parcel in) {
        followed = in.readByte() != 0;
        msg = in.readString();
        desc = in.readString();
        ID=in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (followed ? 1 : 0));
        dest.writeString(msg);
        dest.writeString(desc);
        dest.writeInt(ID);
    }
}