const TRANSLATION_ATTR = 'translation-key';

// Language translations
let translations = {
    "languages": {
        "en": {
            "strings": {
                // Home Page
                "home-page-title": "Track time easily as possible...",
                "home-page-subtitle": "If you have not an account already. Please, click to \"Register\" button. On the other side click on \"Login\" button.",
                "home-form-button-register": "Register",
                "home-form-button-login": "Login",
                // Login Page
                "login-page-title": "Login page",
                "login-form-username": "User name: ",
                "login-form-password": "Password: ",
                "login-form-button": "Login",
                // Registration Page
                "registration-page-title": "Registration Page",
                "registration-form-username": "User name: ",
                "registration-form-password": "Password: ",
                "registration-form-password-again": "Password again: ",
                "registration-form-button": "Register",
                // Done Page
                "done-button": "Continue",
            }
        },
        "cs": {
            "strings": {
                // Home Page
                "home-page-title": "Trekuj si čas jednoduše...",
                "home-page-subtitle": "Pokud ještě nemáš svůj účet, klikni na \"Registrovat\". Pokud již účet máš, pokračuj klikem na \"Přihlásit\".",
                "home-form-button-register": "Registrovat",
                "home-form-button-login": "Přihlásit",
                // Login Page
                "login-page-title": "Přihlášení",
                "login-form-username": "Uživatelské jméno: ",
                "login-form-password": "Heslo: ",
                "login-form-button": "Přihlásit",
                // Registration Page
                "registration-page-title": "Registrace",
                "registration-form-username": "Uživatelské jméno: ",
                "registration-form-password": "Heslo: ",
                "registration-form-password-again": "Heslo znovu: ",
                "registration-form-button": "Registrovat",
                // Done Page
                "done-button": "Pokračovat"
            }
        }
    }
}

// Apply language value to the content
document.addEventListener('DOMContentLoaded', () => {
    let zone = getZone();
    applyStrings(zone);
});

function getZone() {
    let lang = navigator.language;
    let locale = Intl.getCanonicalLocales(lang);
    let localeShort = locale.toString().substring(0, 2);

    if (translations.languages[localeShort]) {
        return localeShort;
    } else {
        return "en";
    }
}

function applyStrings(locale) {
    document.body.querySelectorAll(`[${TRANSLATION_ATTR}]`).forEach(element => {
        let key = element.getAttribute(TRANSLATION_ATTR);
        if (key) {

            let translation = translations.languages[locale].strings[key];

            if (element.nodeName == "INPUT" && element.type == "submit") {
                element.value = translation;
            } else {
                element.textContent = translation;
            }
        }
    });
}