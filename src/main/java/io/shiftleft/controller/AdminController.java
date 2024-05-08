package io.shiftleft.controller;

// ...

@Controller
public class AdminController {
    // ...

    @RequestMapping(value = "/admin/login", method = RequestMethod.POST)
    public String doPostLogin(@CookieValue(value = "auth", defaultValue = "notset", secure = true) String auth, @RequestBody String password, HttpServletResponse response, HttpServletRequest request) throws Exception {
        String succ = "redirect:/admin/printSecrets";

        try {
            if (!auth.equals("notset")) {
                // No cookie no fun
                if (isAdmin(auth)) {
                    request.getSession().setAttribute("auth", auth);
                    return succ;
                }
            }

            // split password=value
            String[] pass = password.split("=");

            if (pass.length != 2) {
                return fail;
            }

            // compare pass
            if (pass[1] != null && pass[1].length() > 0 && pass[1].equals("shiftleftsecret")) {
                AuthToken authToken = new AuthToken(AuthToken.ADMIN);
                Cookie cookie = new Cookie("auth", Base64.getEncoder().encodeToString(authToken.toByteArray()));
                cookie.setHttpOnly(true);
                response.addCookie(cookie);
                request.getSession().setAttribute("auth", auth);

                return succ;
            }
            return fail;
        } catch (Exception ex) {
            ex.printStackTrace();
            // no succ == fail
            return fail;
        }
    }

    // ...

    @RequestMapping(value = "/admin/login", method = RequestMethod.GET)
    public String doGetLogin(HttpServletResponse response, HttpServletRequest request) {
        return "redirect:/";
    }
}
