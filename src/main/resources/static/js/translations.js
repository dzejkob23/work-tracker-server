const TRANSLATION_ATTR = 'translation-key';

// Language translations
let translations = {
    "languages": {
        "en": {
            "strings": {
                "login-form-username": "User name: ",
                "login-form-password": "Password: ",
                "login-form-button": "Login"
            }
        },
        "cs": {
            "strings": {
                "login-form-username": "Uživatelské jméno:",
                "login-form-password": "Heslo: ",
                "login-form-button": "Přihlásit"
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