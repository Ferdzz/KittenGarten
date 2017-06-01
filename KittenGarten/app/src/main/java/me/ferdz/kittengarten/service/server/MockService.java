//package me.ferdz.kittengarten.service.server;
//
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.provider.MediaStore;
//
//import java.io.BufferedOutputStream;
//import java.io.ByteArrayOutputStream;
//import java.text.BreakIterator;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//import java.util.Random;
//
//import me.ferdz.kittengarten.model.Kitten;
//import me.ferdz.kittengarten.model.MinKitten;
//import me.ferdz.kittengarten.model.MinUser;
//import me.ferdz.kittengarten.model.User;
//import me.ferdz.kittengarten.service.client.IService;
//import retrofit2.Call;
//import retrofit2.http.Body;
//import retrofit2.http.Path;
//import retrofit2.mock.BehaviorDelegate;
//
///**
// *  !!!!!!!! IMPORTANT !!!!!!!!
// *
// *      KEEP THIS ISOLATED
// *        FROM THE REST
// *         OF THE CODE
// *
// *          U DINGUS
// *
// *  !!!!!!!! IMPORTANT !!!!!!!!
// *
// * Created by 1452284 on 2016-09-15.
// */
//public class MockService  implements IService {
//    BehaviorDelegate<IService> delegate;
//
//    private ArrayList<Kitten> kittens;
//    private ArrayList<User> users;
//    private Random rand;
//
//    public MockService(BehaviorDelegate<IService> delegate) {
//        this.delegate = delegate;
//        this.rand = new Random();
//
//        User user = new User(0, "thanks");
//        user.setEmail("test@gmail.com");
//        user.setPassword("d");
//        this.users = new ArrayList<>(Arrays.asList(user));
//
//        this.kittens = new ArrayList<>();
//        this.kittens.add(new Kitten(0, "Kit 1"));
//        this.kittens.add(new Kitten(1, "Kit 2"));
//        this.kittens.add(new Kitten(2, "Kit 3"));
//    }
//
//    @Override
//    public Call<Kitten> addKitten(@Body Kitten kitten) {
//        kitten.setId(rand.nextInt(Integer.MAX_VALUE));
//        kittens.add(kitten);
//        return delegate.returningResponse(kitten).addKitten(kitten);
//    }
//
//    @Override
//    public Call<Void> removeKitten(@Path("kitten") int kittenId) {
//        for (Kitten k : kittens) {
//            if(k.getId() == kittenId) {
//                kittens.remove(k);
//                return delegate.returningResponse(kittenId).removeKitten(kittenId);
//            }
//        }
//        return delegate.returningResponse(null).removeKitten(kittenId);
//    }
//
//    @Override
//    public Call<Kitten> getKitten(@Path("kitten") int kittenId) {
//        for (Kitten k : kittens) {
//            if(k.getId() == kittenId)
//                return delegate.returningResponse(k).getKitten(kittenId); // k should be a new instance of the Kitten
//        }
//        return delegate.returningResponse(null).getKitten(kittenId);
//    }
//
//    @Override
//    public Call<List<MinKitten>> getKittens() {
//        ArrayList<MinKitten> minKittens = new ArrayList<>();
//        for (Kitten k : kittens) {
//            minKittens.add(k.minimalize());
//        }
//
//        return delegate.returningResponse(minKittens).getKittens();
//    }
//
//    @Override
//    public Call<Boolean> rentKitten(@Path("kitten") int kittenId, @Path("user") int userId) {
//        return null;
//    }
//
//    /*
//     *
//     * USERS
//     *
//     */
//
//    @Override
//    public Call<MinUser> registerUser(@Body User user) {
//        user.setId(rand.nextInt(Integer.MAX_VALUE - 1));
//        users.add(user);
//        return delegate.returningResponse(user.minimalize()).logUser(user);
//    }
//
//    @Override
//    public Call<MinUser> logUser(@Body User user) {
//        for (User u: users) {
//            if(u.getPassword().equals(user.getPassword()) && u.getUsername().equalsIgnoreCase(user.getUsername())) {
//                return delegate.returningResponse(u.minimalize()).logUser(user);
//            }
//        }
//        return delegate.returningResponse(null).logUser(user);
//    }
//}
