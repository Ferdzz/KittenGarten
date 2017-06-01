package me.ferdz.kittengartenserver;

import com.google.gson.Gson;
import me.ferdz.kittengartenserver.exception.CookieExpiredException;
import me.ferdz.kittengartenserver.exception.KittenAlreadyRentedException;
import me.ferdz.kittengartenserver.model.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static sun.rmi.transport.TransportConstants.Call;

/**
 * Created by 1452284 on 2016-09-29.
 *
 *
 * UR A MASSIVE GOOMFBALL LMAO AYY
 *
 * YEE
 *
 */
@Path("/")
public class WebService {
    private static final ArrayList<Token> TOKENS = new ArrayList<Token>();
    private static final String COOKIE_ID = "KittenGarten_Cookie";

    private static final Gson gson = new Gson();
    private static final Random rand = new Random();
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static final ArrayList<User> users = new ArrayList<User>();
    private static ArrayList<Kitten> kittens = new ArrayList<Kitten>();

    static {
        User defaultUser = new User(1, "thanks");
        defaultUser.setEmail("thanks@default.com");
        defaultUser.setPassword("d");
        users.add(defaultUser);

        Kitten kitten1 = new Kitten(1, "Kit 1");
        kitten1.setWeight(5);
        kittens.add(kitten1);
        Kitten kitten2 = new Kitten(2, "Kit 2");
        kitten1.setWeight(6);
        kittens.add(kitten2);
        Kitten kitten3 = new Kitten(3, "Kit 3");
        kitten1.setWeight(9);
        kittens.add(kitten3);
    }

    //region Kittens
    @POST
    @Path("kitten/add")
    public Kitten addKitten(Kitten kitten) {
        System.out.println("Tried adding kitten");
        delay();

        if(kitten.getName().isEmpty())
            throw new IllegalArgumentException("Name is invalid");
        else if (kitten.getBirthDate().after(new Date()))
            throw new IllegalArgumentException("Date cannot be in the future");
        else if (kitten.getWeight() <= 0)
            throw new IllegalArgumentException("Weight cannot be negative");

        kitten.setId(rand.nextInt(Integer.MAX_VALUE));
        kittens.add(kitten);
        return kitten;
    }

    @POST
    @Path("kitten/remove/{kitten}")
    public void removeKitten(@PathParam("kitten") int kittenId) {
        System.out.println("Tried removing kitten");
        delay();

        for (int i = kittens.size() - 1; i >= 0; i--) {
            if(kittens.get(i).getId() == kittenId) {
                kittens.remove(i);
                return;
            }
        }
        throw new IllegalArgumentException("Could not find kitten");
    }

    @GET
    @Path("kitten/get/{kitten}")
    public Kitten getKitten(@PathParam("kitten") int kittenId) {
        System.out.println("Getting kitten " + kittenId);
        delay();

        for (Kitten k : kittens) {
            if(kittenId == k.getId())
                return k;
        }
        throw new IllegalArgumentException("Kitten with id " + kittenId + " does not exist");
    }

    @GET
    @Path("kitten/getall")
    public List<MinKitten> getKittens() {
        System.out.println("Getting all kittens");
        delay();

        ArrayList<MinKitten> minKittens = new ArrayList<MinKitten>();
        for (Kitten k : kittens) {
            minKittens.add(k.minimalize());
        }

        return minKittens;
    }

    @POST
    @Path("kitten/rent/{kitten}/{user}")
    public void rentKitten(@CookieParam(COOKIE_ID) Cookie cookie, @PathParam("kitten") int kittenId) {
        System.out.println("Tried renting kitten");
        delay();

        Token token = getTokenFromCookie(cookie);

        User user = null;
        for (User u: users) {
            if(u.getId() == token.userID) {
                user = u;
                break;
            }
        }
        Kitten kitten = null;
        for (Kitten k: kittens) {
            if(k.getId() == kittenId) {
                kitten = k;
            }
        }
        if(user == null || kitten == null)
            throw new IllegalArgumentException();
        else if (kitten.getRenter() != null)
            throw new KittenAlreadyRentedException();

        kitten.setRenter(user);
    }

    @POST
    @Path("kitten/return/{kitten}/{user}")
    public void returnKitten(@CookieParam(COOKIE_ID) final Cookie cookie, @PathParam("kitten") int kittenId) throws IllegalAccessException {
        System.out.println("Tried returning kitten");
        delay();

        Token token = getTokenFromCookie(cookie);

        for (Kitten k: kittens) {
            if(k.getId() == kittenId) {
                if(k.getRenter().getId() == token.userID) {
                    k.setRenter(null);
                    return;
                } else {
                    throw new IllegalAccessException("You do not own this kitten");
                }
            }
        }
        throw new IllegalArgumentException("Kitten was not found");
    }
    //endregion

    //region User
    @POST
    @Path("user/add")
    public void registerUser(User user) {
        System.out.println("Tried registering user");
        delay();

        if(user.getUsername().isEmpty())
            throw new IllegalArgumentException("Username is invalid");
        else if (user.getPassword().isEmpty())
            throw new IllegalArgumentException("Password is invalid");
        Matcher matcher = EMAIL_PATTERN.matcher(user.getEmail());
        if(!matcher.find())
            throw new IllegalArgumentException("Email is invalid");

        for (User u : users) {
            if(u.getEmail().equals(user.getEmail()) || u.getUsername().equals(user.getUsername())) {
                throw new IllegalArgumentException("User with email already exist");
            }
        }
        user.setId(rand.nextInt(Integer.MAX_VALUE - 1));
        users.add(user);
    }

    @POST
    @Path("user/logout")
    public Response logoutUser(@CookieParam(COOKIE_ID) final Cookie cookie) {
        System.out.println("Tried logging out");
        delay();

        Token token = TOKENS.stream().filter(x -> x.ID.equals(cookie.getValue())).findAny().get();
        TOKENS.remove(token);

        NewCookie newCookie = new NewCookie(COOKIE_ID, token.ID, "/", "", "id token", 0, false);
        return Response.ok().cookie(newCookie).build();
    }

    @POST
    @Path("user/log")
    public Response logUser(User user) {
        System.out.println("Tried logging in user");
        delay();

        String token = UUID.randomUUID().toString();
        for (User u : users) {
            if (user.getPassword().equals(u.getPassword()) && user.getUsername().equals(u.getUsername())) {
                NewCookie cookie = new NewCookie(COOKIE_ID, token, "/", "", "id token", 450000, false);
                Token token1 = new Token();
                token1.expiration = new Date(System.currentTimeMillis() + 450000);
                token1.ID = token;
                token1.userID = u.getId();
                TOKENS.add(token1);

                return Response.ok(gson.toJson(u.minimalize()), MediaType.APPLICATION_JSON)
                        .cookie(cookie)
                        .build();
            }
        }

        throw new IllegalArgumentException();
    }
    //endregion

    private Token getTokenFromCookie(Cookie cookie) {
        Token token = TOKENS.stream().filter(x -> x.ID.equals(cookie.getValue())).findAny().get();
        if(token == null)
            throw new RuntimeException("Cookie does not exist");
        if(token.expiration.before(new Date()))
            throw new CookieExpiredException("Loggin ran out");
        return token;
    }

    private void delay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
