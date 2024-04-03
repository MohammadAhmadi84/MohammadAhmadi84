import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        MusicModel musicModel = new MusicModel("a", "ab", "2025/03/23", Genre.HIPHOP, "dfld");
        System.out.println(musicModel.getLikeCounter());
        ListenerController.getListenerController().likeAudio(musicModel.getIdentityNumber());
        System.out.println(musicModel.getSharingDate().getYear() + 1900);


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
                dataBase.Audios[i] = new ArrayList<>();
            }
        }
        return dataBase;
    }

    private ArrayList<UserAccountModel>[] Users = new ArrayList[5];
    private ArrayList<AudioModel>[] Audios = new ArrayList[2];
    private ArrayList<ReportModel> Reports = new ArrayList<>();
    private ArrayList<PlayListModel>playLists=new ArrayList<>();

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

    public void setPlayLists(PlayListModel playLists) {
        this.playLists.add(this.playLists.size(),playLists);
    }
    public String getPlayLists(){
        String result="";
        for (PlayListModel playlist:playLists){
            result+=playlist.toString()+"\n";
        }
        return result;
    }
    public String getPlayLists(String playlistName){
        String result="";
        for (PlayListModel playlist:playLists){
            if (playlist.getPlaylistName().contains(playlistName)){
                result+=playlist.toString()+"\n"+playlist.getAudio()+"\n";
            }
        }
        return result;
    }

    public void setReports(ReportModel reports) {
        Reports.add(Reports.size(), reports);
    }

    public boolean checkListener(UserAccountModel listener) {
        for (UserAccountModel a : Users[1]) {
            if (a.getUserName().equals(listener.getUserName())) {
                return true;
            }
        }
        for (UserAccountModel a : Users[2]) {
            if (a.getUserName().equals(listener.getUserName())) {
                return true;
            }
        }
        return false;
    }

    public int findListener(String username, String password) {

        for (UserAccountModel a : Users[1]) {
            if (a.getUserName().equals(username) && a.getPassword().equals(password)) {
                return 2;
            }
        }
        for (UserAccountModel a : Users[2]) {
            if (a.getUserName().equals(username) && a.getPassword().equals(password)) {
                return 2;
            }
        }

        return 1;
    }
    public String audioToString(){
        String result="";
        for (AudioModel audioModel:Audios[0]){
            result+=audioModel.toString()+"\n";
        }
        for (AudioModel audioModel:Audios[1]){
            result+=audioModel.toString()+"\n";
        }
        return result;
    }

    public AudioModel getAudio(String audioIdentity) {
        if (Audios[0] != null) {
            for (AudioModel music : Audios[0]) {
                if (music.getIdentityNumber().equals(audioIdentity)) {
                    return music;
                }
            }
        }

        for (AudioModel music : Audios[1]) {
            if (music.getIdentityNumber().equals(audioIdentity)) {
                return music;
            }
        }
        return null;
    }

    public String search(String userInput) {
        String result = "";
        for (UserAccountModel user : Users[3]) {
            if (user.getFullName().contains(userInput)) {
                result += user.toString() + "\n";
            }
        }
        for (UserAccountModel user : Users[4]) {
            if (user.getFullName().contains(userInput)) {
                result += user.toString() + "\n";
            }
        }
        for (AudioModel audio : Audios[0]) {
            if (audio.getAudioName().contains(userInput)) {
                result += audio.toString() + "\n";
            }
        }
        for (AudioModel audio : Audios[1]) {
            if (audio.getAudioName().contains(userInput)) {
                result += audio.toString() + "\n";
            }
        }

        return result;
    }

    public String filterByArtist(String name) {
        String result = "";
        for (AudioModel audio : Audios[0]) {
            if (audio.getArtistName().contains(name)) {
                result += audio.toString() + "\n";
            }
        }
        for (AudioModel audio : Audios[1]) {
            if (audio.getArtistName().contains(name)) {
                result += audio.toString() + "\n";
            }
        }
        return result;
    }

    public String filterByGenre(String genre) {
        String result = "";
        for (AudioModel audio : Audios[0]) {
            if (String.valueOf(audio.getGenre()).equalsIgnoreCase(genre)) {
                result += audio.toString();
            }
        }
        for (AudioModel audio : Audios[0]) {
            if (String.valueOf(audio.getGenre()).equalsIgnoreCase(genre)) {
                result += audio.toString();
            }
        }
        return result;
    }

    public String filterByDate(int year, int month, int day) {
        String result = "";
        for (AudioModel audio : Audios[0]) {
            if ((audio.getSharingDate().getYear() + 1900) < year) {
                result += audio.toString() + "\n";
            } else if (audio.getSharingDate().getYear() == year) {
                if (month > audio.getSharingDate().getMonth() + 1) {
                    result += audio.toString() + "\n";
                } else if (month == audio.getSharingDate().getMonth() + 1 && day >= audio.getSharingDate().getDate()) {
                    result += audio.toString() + "\n";
                }
            }
        }
        for (AudioModel audio : Audios[1]) {
            if ((audio.getSharingDate().getYear() + 1900) < year) {
                result += audio.toString() + "\n";
            } else if (audio.getSharingDate().getYear() + 1900 == year) {
                if (month > audio.getSharingDate().getMonth() + 1) {
                    result += audio.toString() + "\n";
                } else if (month == audio.getSharingDate().getMonth() + 1 && day >= audio.getSharingDate().getDate()) {
                    result += audio.toString() + "\n";
                }
            }
        }
        return result;
    }
    public void sortByLike(){
        for (int i=0;i<Audios[0].size()-1;i++){
            for (int j=0;j<Audios[0].size()-i-1;j++){
                if (Audios[0].get(j).getLikeCounter()<Audios[0].get(j+1).getLikeCounter()){
                    AudioModel tmp=Audios[0].get(j);
                    Audios[0].add(j,Audios[0].get(j+1));
                    Audios[0].add(j+1,Audios[0].get(j));
                }
            }
        }
        for (int i=0;i<Audios[1].size()-1;i++){
            for (int j=0;j<Audios[1].size()-i-1;j++){
                if (Audios[1].get(j).getLikeCounter()<Audios[1].get(j+1).getLikeCounter()){
                    AudioModel tmp=Audios[1].get(j);
                    Audios[1].add(j,Audios[1].get(j+1));
                    Audios[1].add(j+1,Audios[1].get(j));
                }
            }
        }

    }
    public void sortByPlaying(){
        for (int i=0;i<Audios[0].size()-1;i++){
            for (int j=0;j<Audios[0].size()-i-1;j++){
                if (Audios[0].get(j).getPlayCounter()<Audios[0].get(j+1).getLikeCounter()){
                    AudioModel tmp=Audios[0].get(j);
                    Audios[0].add(j,Audios[0].get(j+1));
                    Audios[0].add(j+1,Audios[0].get(j));
                }
            }
        }
        for (int i=0;i<Audios[1].size()-1;i++){
            for (int j=0;j<Audios[1].size()-i-1;j++){
                if (Audios[1].get(j).getPlayCounter()<Audios[1].get(j+1).getPlayCounter()){
                    AudioModel tmp=Audios[1].get(j);
                    Audios[1].add(j,Audios[1].get(j+1));
                    Audios[1].add(j+1,Audios[1].get(j));
                }
            }
        }
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
        DataBase.getDataBase().setUsers(this);
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

    public Date getBirthday() {
        return birthday;
    }

    @Override
    public String toString() {
        return "UserAccountModel{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthday=" + birthday +
                ", dateModifier='" + dateModifier + '\'' +
                '}';
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
        DataBase.getDataBase().setAudios(this);
    }

    final private String identityNumber;
    final private String audioName;
    final private String artistName;
    private long playCounter;
    private long likeCounter;
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

    public long getPlayCounter() {
        return playCounter;
    }

    public long getLikeCounter() {
        return likeCounter;
    }

    public Date getSharingDate() {
        return sharingDate;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setPlayCounter(long playCounter) {
        this.playCounter = playCounter;
    }

    public void setLikeCounter(long likeCounter) {
        this.likeCounter = likeCounter;
    }

    @Override
    public String toString() {
        return "AudioModel{" +
                "identityNumber='" + identityNumber + '\'' +
                ", audioName='" + audioName + '\'' +
                ", artistName='" + artistName + '\'' +
                ", playCounter=" + playCounter +
                ", likeCounter=" + likeCounter +
                ", sharingDate=" + sharingDate +
                ", genre=" + genre +
                '}';
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
        PlayListModel.playlistCounter++;
        this.playListIdentity = String.valueOf(playlistName.length()) + String.valueOf(makerName.length())+ String.valueOf(PlayListModel.playlistCounter);
    }

    private String playListIdentity;
    private String playlistName;
    private String makerName;
    ArrayList<AudioModel> audio = new ArrayList<>();
    private static long playlistCounter=0;

    @Override
    public String toString() {
        return "PlayListModel{" +
                "playListIdentity='" + playListIdentity + '\'' +
                ", playlistName='" + playlistName + '\'' +
                ", makerName='" + makerName + '\'' +
                '}';
    }

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
    public long getNumberOfAudio(){
        return audio.size();
    }
    public String getAudio(){
        String result="";
        for (AudioModel audioModel:audio){
            result+=audioModel.toString()+"\n";
        }
        return result;
    }
    public void setAudio(AudioModel audio){
        this.audio.add(this.audio.size(),audio);
    }
}

abstract class ListenerModel extends UserAccountModel {
    public ListenerModel(String userName, String password, String fullName, String email, String phoneNumber, String dateModifier, Date expiryDate) {
        super(userName, password, fullName, email, phoneNumber, dateModifier);
        this.userAccountCredentials = 50;
        this.expiryDate = expiryDate;
    }


    private double userAccountCredentials;
    private Date expiryDate;
    private ArrayList<Genre> favoriteGenre = new ArrayList<>();
    private Map<AudioModel, Long> musicPlayCounter = new HashMap<>();
    private ArrayList<ArtistModel> following = new ArrayList<>();
    private ArrayList<PlayListModel>playLists=new ArrayList<>();
    public String getPlaylists(){
        String result="";
        for (PlayListModel playlist:playLists){
            result+=playlist.toString()+"\n";
        }
        return result;
    }
    public PlayListModel findPlayList(String playlistId){
        for (PlayListModel playListModel:playLists){
            if (playlistId.equals(playListModel.getPlayListIdentity())){
                return playListModel;
            }
        }
        return null;
    }

    public double getUserAccountCredentials() {
        return userAccountCredentials;
    }


    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setMusicPlayCounter(AudioModel audioModel) {
        long newValue = musicPlayCounter.get(audioModel);
        musicPlayCounter.put(audioModel, newValue + 1);
    }

    public String getFollowing() {
        String tmp = "";
        for (ArtistModel artist : following) {
            tmp += ("Name: " + artist.getFullName() + "\n" + "Bio" + artist.getBio() + "\n");
        }
        return tmp;
    }

    public void setUserAccountCredentials(double userAccountCredentials) {
        this.userAccountCredentials = userAccountCredentials;
    }

    public void setFavoriteGenre(ArrayList<Genre> favoriteGenre) {
        this.favoriteGenre = favoriteGenre;
    }

    public ArrayList<Genre> getFavoriteGenre() {
        return favoriteGenre;
    }
}

final class FreeListenerModel extends ListenerModel {
    public FreeListenerModel(String userName, String password, String fullName, String email, String phoneNumber, String dateModifier, int musicAddCounter, int playlistCounter) {
        super(userName, password, fullName, email, phoneNumber, dateModifier, null);
        this.musicAddCounter = musicAddCounter;
        this.playlistCounter = playlistCounter;
    }

    private final static long MusicAddLimit = 10;
    private final static long playListMakeLimit = 3;
    private int musicAddCounter=0;
    private int playlistCounter=0;

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

    public void setMusicAddCounter(int musicAddCounter) {
        this.musicAddCounter = musicAddCounter;
    }

    public void setPlaylistCounter(int playlistCounter) {
        this.playlistCounter = playlistCounter;
    }

    @Override
    public String toString() {
        return "FreeListenerModel{" +
                "musicAddCounter=" + musicAddCounter +
                ", playlistCounter=" + playlistCounter +
                '}';
    }
}

final class PremiumListenerModel extends ListenerModel {
    public PremiumListenerModel(String userName, String password, String fullName, String email, String phoneNumber, String dateModifier, Date expiryDate) {
        super(userName, password, fullName, email, phoneNumber, dateModifier, expiryDate);
    }

    private long remainingDys;

    public long getRemainingDys() {
        return remainingDys;
    }

}

class ListenerController {
    private static ListenerController listenerController;

    public static ListenerController getListenerController() {
        if (listenerController == null) {
            listenerController = new ListenerController();
        }
        return listenerController;
    }

    public boolean signIn(String userName, String password, String fullName, String email, String phoneNumber, String dateModifier, int musicAddCounter, int playlistCounter) {
        FreeListenerModel tmp = new FreeListenerModel(userName, password, fullName, email, phoneNumber, dateModifier, musicAddCounter, playlistCounter);
        if (!(DataBase.getDataBase().checkListener(tmp))) {
            DataBase.getDataBase().setUsers(tmp);
            return true;
        }
        return false;
    }

    public int logIn(String username, String password) {
        return DataBase.getDataBase().findListener(username, password);
    }

    public boolean likeAudio(String audioIdentity) {
        AudioModel tmpAudio = DataBase.getDataBase().getAudio(audioIdentity);
        if (tmpAudio == null) {
            return false;
        }
        tmpAudio.setLikeCounter(tmpAudio.getLikeCounter() + 1);
        return true;
    }

    public boolean playMusic(String audioIdentity, ListenerModel listenerModel) {
        AudioModel audioModel = DataBase.getDataBase().getAudio(audioIdentity);
        if (audioModel == null) {
            return false;
        }
        audioModel.setPlayCounter(audioModel.getPlayCounter() + 1);
        listenerModel.setMusicPlayCounter(audioModel);
        return true;
    }

    public String searchMusic_Artist(String name) {
        return DataBase.getDataBase().search(name);
    }

    public String artistFilter(String artistName) {
        return DataBase.getDataBase().filterByArtist(artistName);
    }

    public String filterByGenre(String genre) {
        return DataBase.getDataBase().filterByGenre(genre);
    }

    public String filterByDate(int year, int month, int day) {
        return DataBase.getDataBase().filterByDate(year, month, day);
    }
    public String getListenerFollowing(ListenerModel listenerModel) {
        return listenerModel.getFollowing();
    }
    public String sortByLike(){
        DataBase.getDataBase().sortByLike();
        return DataBase.getDataBase().audioToString();
    }
    public String sortByPlay(){
        DataBase.getDataBase().sortByPlaying();
        return DataBase.getDataBase().audioToString();
    }
    public boolean MakePlaylist(ListenerModel listener,String playListName){
        return true;
    }
    public boolean addMusic(ListenerModel listener,String playListId,String AudioId){
        return true;
    }

    public String showPlayList(String playListName){
        return DataBase.getDataBase().getPlayLists(playListName);
    }
    public String showUserPlayLists(ListenerModel listener){
        return listener.getPlaylists();
    }
    public boolean creditImprove(ListenerModel listener,double price){
        if (DataBase.getDataBase().checkListener(listener)){
            listener.setUserAccountCredentials(listener.getUserAccountCredentials()+price);
            return true;
        }
        return false;
    }
    public String getSuggestion(ListenerModel listener){
        String result="";
        return result;
    }
    public String showAccountInfo(ListenerModel listenerModel){
        String result="";
        result+="UserName: "+listenerModel.getUserName()+"\"
                +"Password: "+listenerModel.getPassword()+"\"
                +"Birthday: "+listenerModel.getBirthday()+"\"
                +"FullName: "+listenerModel.getFullName()+"\"
                +"Phone number"+listenerModel.getPhoneNumber()+"\"
                +"Email"+listenerModel.getEmail();

        return result;
    }
    public void setFavoriteGenre(ListenerModel listener,ArrayList<String>genre){
        ArrayList<Genre>genres=new ArrayList<>();
        for (String userGenre:genre){
            switch (userGenre){
                case "HipHop":
                    genres.add(Genre.HIPHOP);
                    break;
                case "Rock":
                    genres.add(Genre.ROCK);
                    break;
                case "Pop":
                    genres.add(Genre.POP);
                    break;
                case "Jazz":
                    genres.add(Genre.JAZZ);
                    break;
                case "Country":
                    genres.add(Genre.COUNTRY);
                    break;
                case "True Crime":
                    genres.add(Genre.TRUE_CRIME);
                    break;
                case "Society":

            }
        }
    }
}
class FreeListenerController extends ListenerController{
    @Override
    public boolean MakePlaylist(ListenerModel listener,String playListName) {
        FreeListenerModel tmp=(FreeListenerModel) listener;
        if (tmp.getPlaylistCounter()<3) {
            PlayListModel newPlayList = new PlayListModel(playListName, listener.getFullName());
            tmp.setPlaylistCounter(tmp.getPlaylistCounter()+1);
            return true;
        }
        return false;
    }

    @Override
    public boolean addMusic(ListenerModel listener, String playListId,String AudioId) {
        PlayListModel tmpPlaylist=listener.findPlayList(playListId);
        FreeListenerModel tmpListener=(FreeListenerModel) listener;
        if (tmpPlaylist==null){
            return false;
        }
        if (tmpPlaylist.getNumberOfAudio()<10){
            if (DataBase.getDataBase().getAudio(AudioId)!=null) {
                tmpPlaylist.setAudio(DataBase.getDataBase().getAudio(AudioId));
                return true;
            }

        }
        return false;
    }
}
class PremiumController extends ListenerController{
    @Override
    public boolean MakePlaylist(ListenerModel listener, String playListName) {
        PremiumListenerModel tmp=(PremiumListenerModel) listener;
        PlayListModel newPlayList = new PlayListModel(playListName, listener.getFullName());
        return true;
    }

    @Override
    public boolean addMusic(ListenerModel listener, String playListId, String AudioId) {
        PremiumListenerModel tmpListener=(PremiumListenerModel) listener;
        PlayListModel tmpPlaylist=listener.findPlayList(playListId);
        if (tmpPlaylist!=null && DataBase.getDataBase().getAudio(AudioId)!=null){
            tmpPlaylist.setAudio(DataBase.getDataBase().getAudio(AudioId));
            return true;
        }

        return false;
    }
}

class AudioController {
    private static AudioController audioController;

    public static AudioController getAudioController() {
        if (audioController == null) {
            audioController = new AudioController();
        }
        return audioController;
    }

}
