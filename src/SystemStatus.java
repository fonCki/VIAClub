import java.io.Serializable;
import java.util.Locale;

public class SystemStatus implements Serializable {
    private boolean deleted;
    private boolean available;
    private boolean unavailable;

    public SystemStatus() {
        this.available = true;
        this.unavailable = !(this.available);
        this.deleted = !(this.available);
    }

    public void setAvailable() {
        this.available = true;
        this.unavailable = !(this.available);
        this.deleted = !(this.available);
    }

    public void setUnavailable() {
        this.unavailable = true;
        this.available = false;
        this.deleted = false;
    }

    public void setDeleted() {
        this.deleted = true;
        this.unavailable = false;
        this.available = false;
    }

    public void setStatus(String status) {
        switch (status.toLowerCase()) {
            case "unavailable" :
            case "injured" :
            case "suspended" : {
                this.setUnavailable();
                break;
            }
            case "deleted" : {
                this.setDeleted();
                break;
            }
            default: setAvailable();
        }
    }

    public boolean isAvailable() {
        return this.available;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public boolean isUnavailable() {
        return this.unavailable;
    }

    public String getStatus() {
        if (this.available) return "Available";
        if (this.unavailable) return "Unavailable";
        if (this.deleted) return "Deleted";
        return "System Status Error";
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SystemStatus)) {
            return false;
        } else {
            return ((SystemStatus) obj).deleted == this.deleted &&
                    ((SystemStatus) obj).available == this.available &&
                    ((SystemStatus) obj).unavailable == this.unavailable;
        }
    }

}
