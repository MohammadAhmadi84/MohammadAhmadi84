import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) {

    }
}

final class DataBase {
    private static DataBase dataBase;

    public static DataBase getDataBase() {
        if (dataBase == null) {
            dataBase = new DataBase();
            for (int i = 0; i < 5; i++) {
                dataBase.Users[i] = new ArrayList<>();
            }
            for (int i = 0; i < 2; i++) {
                dataBase.Users[i] = new ArrayList<>();
            }
        }
        return dataBase;
    }

    private ArrayList<UserAccountModel>[] Users = new ArrayList[5];
    private ArrayList<AudioModel>[] Audios = new ArrayList[2];
    private ArrayList<ReportModel> Reports = new ArrayList<>();

    public void setUsers(UserAccountModel users) {
        if (users instanceof AdminModel) {
            Users[0].add(Users[0].size(), users);
        } else if (users instanceof FreeListenerModel) {
            Users[1].add(Users[1].size(), users);
        } else if (users instanceof PremiumListenerModel) {
            Users[2].add(Users[2].size(), users);
        } else if (users instanceof SingerModel) {
            Users[3].add(Users[3].size(), users);
        } else if (users instanceof PodcasterModel) {
            Users[4].add(Users[4].size(), users);
        }
    }

    public void setAudios(AudioModel audios) {
        if (audios instanceof MusicModel) {
            Audios[0].add(Audios[0].size(), audios);
        } else if (audios instanceof PodcastModel) {
            Audios[1].add(Audios[1].size(), audios);
        }
    }

    public void setReports(ReportModel reports) {
        Reports.add(Reports.size(), reports);
    }

    public boolean checkListener(UserAccountModel listener) {
        for (UserAccountModel a : Users[1]) {
            if (a.getUserName().equals(listener.getUserName())) {
                return false;
            }
        }
        for (UserAccountModel a : Users[2]) {
            if (a.getUserName().equals(listener.getUserName())) {
                return false;
            }
        }
        return true;
    }

    public int findListener(String username, String password) {

        for (UserAccountModel a : Users[1]) {
            if (a.getUserName().equals(username) && a.getPassword().equals(password)){
                return 2;
            }
        }
        for (UserAccountModel a : Users[2]) {
            if (a.getUserName().equals(username) && a.getPassword().equals(password)){
                return 2;
            }
        }

        return 1;
    }
}

enum Genre {
    ROCK, POP, JAZZ, HIPHOP, COUNTRY, TRUE_CRIME, SOCIETY, INTERVIEW, HISTORY;
}

enum PremiumBox {
    ONE_MONTH(5), TWO_MONTH(9), SIX_MONTH(14);
    private int credit;

    PremiumBox(int credit) {
        this.credit = credit;
    }
}

final class ReportModel {
    public ReportModel(UserAccountModel reporter, ArtistModel reportable, String reportCaption) {
        this.reporter = reporter;
        this.reportable = reportable;
        this.reportCaption = reportCaption;
    }

    private UserAccountModel reporter;
    private ArtistModel reportable;
    private String reportCaption;

    public UserAccountModel getReporter() {
        return reporter;
    }

    public ArtistModel getReportable() {
        return reportable;
    }

    public String getReportCaption() {
        return reportCaption;
    }
}

abstract class UserAccountModel {
    public UserAccountModel(String userName, String password, String fullName, String email, String phoneNumber, String dateModifier) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateModifier = dateModifier;
    }

    private String userName;
    private String password;
    private String fullName;
    private String email;
    private String phoneNumber;
    private final Date birthday = new Date();
    private String dateModifier = new SimpleDateFormat("yyyy/MM/dd").format(birthday);

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDateModifier() {
        return dateModifier;
    }
}

final class AdminModel extends UserAccountModel {
    private static AdminModel adminModel;

    private AdminModel(String userName, String password, String fullName, String email, String phoneNumber, String dateModifier) {
        super(userName, password, fullName, email, phoneNumber, dateModifier);
    }

    public static AdminModel adminModel(String userName, String password, String fullName, String email, String phoneNumber, String dateModifier) {
        if (adminModel == null) {
            adminModel = new AdminModel(userName, password, fullName, email, phoneNumber, dateModifier);
        }
        return adminModel;
    }

    public static AdminModel adminModel() {
        if (adminModel == null) {
            return adminModel("0", "1234567890", "00", "00@email.com", "000000000000", "0000/00/00");
        }
        return adminModel;
    }

}

abstract class ArtistModel extends UserAccountModel {
    public ArtistModel(String userName, String password, String fullName, String email, String phoneNumber, String dateModifier, double artistSalary, String bio) {
        super(userName, password, fullName, email, phoneNumber, dateModifier);
        this.artistSalary = artistSalary;
        this.bio = bio;
    }

    private double artistSalary;
    private List<UserAccountModel> followers = new ArrayList<>();
    private String bio;

    public double getArtistSalary() {
        return artistSalary;
    }

    public String showFollowers() {
        String result = "";
        for (UserAccountModel a : followers) {
            result += a.getFullName() + "\n";
        }
        return result;
    }

    public String getBio() {
        return bio;
    }
}

final class SingerModel extends ArtistModel {
    public SingerModel(String userName, String password, String fullName, String email, String phoneNumber, String dateModifier, double artistSalary, String bio) {
        super(userName, password, fullName, email, phoneNumber, dateModifier, artistSalary, bio);
    }

    private ArrayList<AlbumModel> artistAlbums = new ArrayList<>();

    public String getArtistAlbums() {
        String result = "";
        for (AlbumModel a : artistAlbums) {
            result += "Album Name: " + a.getAlbumName() + "\n" + "Album Identity: " + a.getAlbumIdentity() + "\n" + "Artist: " + a.getAlbumArtist();
        }
        return result;
    }

}

final class PodcasterModel extends ArtistModel {
    public PodcasterModel(String userName, String password, String fullName, String email, String phoneNumber, String dateModifier, double artistSalary, String bio) {
        super(userName, password, fullName, email, phoneNumber, dateModifier, artistSalary, bio);
    }

    ArrayList<PodcastModel> podcasts = new ArrayList<>();
}

abstract class AudioModel {
    public AudioModel(String audioName, String artistName, String sharingDate, Genre genre) {
        this.audioName = audioName;
        this.artistName = artistName;
        this.sharingDate = new Date(sharingDate);
        AudioModel.audioMaker++;
        this.identityNumber = String.valueOf(this.sharingDate.getYear() + 1900) + String.valueOf(this.sharingDate.getMonth() + 1) + String.valueOf(this.sharingDate.getDate()) + String.valueOf(AudioModel.audioMaker);
        this.genre = genre;
    }

    final private String identityNumber;
    final private String audioName;
    final private String artistName;
    private String playCounter;
    private String likeCounter;
    private Date sharingDate;
    private Genre genre;
    static private long audioMaker = 0;

    public String getIdentityNumber() {
        return identityNumber;
    }

    public String getAudioName() {
        return audioName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getPlayCounter() {
        return playCounter;
    }

    public String getLikeCounter() {
        return likeCounter;
    }

    public Date getSharingDate() {
        return sharingDate;
    }

    public Genre getGenre() {
        return genre;
    }
}

class MusicModel extends AudioModel {
    public MusicModel(String audioName, String artistName, String sharingDate, Genre genre, String lyrics) {
        super(audioName, artistName, sharingDate, genre);
        this.lyrics = lyrics;
    }

    private String lyrics;

    public String getLyrics() {
        return lyrics;
    }
}

final class PodcastModel extends AudioModel {
    public PodcastModel(String audioName, String artistName, String sharingDate, Genre genre, String caption) {
        super(audioName, artistName, sharingDate, genre);
        this.caption = caption;
    }

    private String caption;

    public String getCaption() {
        return caption;
    }
}

final class AlbumModel {
    public AlbumModel(String albumName, String albumArtist) {
        this.albumName = albumName;
        this.albumArtist = albumArtist;
        AlbumModel.albumCounter++;
        this.albumIdentity = String.valueOf(albumArtist.length()) + String.valueOf(albumName.length()) + "/" + String.valueOf(AlbumModel.albumCounter);
    }

    private final String albumIdentity;
    private final String albumName;
    private String albumArtist;
    private ArrayList<MusicModel> albumMusics = new ArrayList<>();
    private static long albumCounter = 0;

    public String showMusic() {
        String result = "";
        for (MusicModel a : albumMusics) {
            result += "Music Name: " + a.getAudioName() + "\n" + "Music Artist Name: " + a.getArtistName() + "\n";
        }
        return result;
    }

    public String getAlbumIdentity() {
        return albumIdentity;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getAlbumArtist() {
        return albumArtist;
    }

    public static long getAlbumCounter() {
        return albumCounter;
    }

}

final class PlayListModel {
    public PlayListModel(String playlistName, String makerName) {
        this.playlistName = playlistName;
        this.makerName = makerName;
        this.playListIdentity = String.valueOf(playlistName.length()) + String.valueOf(makerName.length()) + "/" + String.valueOf(PlayListModel.playlistCounter);
    }

    private String playListIdentity;
    private String playlistName;
    private String makerName;
    ArrayList<AudioModel> audio = new ArrayList<>();
    private static long playlistCounter;

    public String showAudio() {
        String result = "";
        for (AudioModel a : audio) {
            result += "Audio Name: " + a.getAudioName() + "\n";
        }
        return result;
    }

    public String getPlayListIdentity() {
        return playListIdentity;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public String getMakerName() {
        return makerName;
    }

    public static long getPlaylistCounter() {
        return playlistCounter;
    }
}

abstract class ListenerModel extends UserAccountModel {
    public ListenerModel(String userName, String password, String fullName, String email, String phoneNumber, String dateModifier, Date expiryDate, ArrayList<Genre> favoriteGenre) {
        super(userName, password, fullName, email, phoneNumber, dateModifier);
        this.userAccountCredentials = 50;
        this.expiryDate = expiryDate;
        this.favoriteGenre = favoriteGenre;
    }


    private double userAccountCredentials;
    private Date expiryDate;
    private ArrayList<Genre> favoriteGenre = new ArrayList<>();
    private Map<String, Long> musicPlayCounter = new HashMap<>();

    public double getUserAccountCredentials() {
        return userAccountCredentials;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

}

final class FreeListenerModel extends ListenerModel {
    public FreeListenerModel(String userName, String password, String fullName, String email, String phoneNumber, String dateModifier, ArrayList<Genre> favoriteGenre, int musicAddCounter, int playlistCounter) {
        super(userName, password, fullName, email, phoneNumber, dateModifier, null, favoriteGenre);
        this.musicAddCounter = musicAddCounter;
        this.playlistCounter = playlistCounter;
    }

    private final static long MusicAddLimit = 100;
    private final static long playListMakeLimit = 20;
    private int musicAddCounter;
    private int playlistCounter;

    public int getMusicAddCounter() {
        return musicAddCounter;
    }

    public int getPlaylistCounter() {
        return playlistCounter;
    }

    public static long getMusicAddLimit() {
        return FreeListenerModel.MusicAddLimit;
    }

    public static long getPlayListMakeLimit() {
        return FreeListenerModel.MusicAddLimit;
    }

}

final class PremiumListenerModel extends ListenerModel {
    public PremiumListenerModel(String userName, String password, String fullName, String email, String phoneNumber, String dateModifier, Date expiryDate, ArrayList<Genre> favoriteGenre) {
        super(userName, password, fullName, email, phoneNumber, dateModifier, expiryDate, favoriteGenre);
    }

    private long remainingDys;

    public long getRemainingDys() {
        return remainingDys;
    }

}

abstract class listenerController {
    public boolean signIn(String userName, String password, String fullName, String email, String phoneNumber, String dateModifier, ArrayList<Genre> favoriteGenre, int musicAddCounter, int playlistCounter) {
        FreeListenerModel tmp = new FreeListenerModel(userName, password, fullName, email, phoneNumber, dateModifier, favoriteGenre, musicAddCounter, playlistCounter);
        if (DataBase.getDataBase().checkListener(tmp)) {
            DataBase.getDataBase().setUsers(tmp);
            return true;
        }
        return false;
    }

    public int logIn(String username, String password) {
        return DataBase.getDataBase().findListener(username, password);
    }

}