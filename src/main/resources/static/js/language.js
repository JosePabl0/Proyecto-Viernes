document.addEventListener("DOMContentLoaded", () => {

    const languageButtons = [
        document.querySelector("#language-toggle"),
        document.querySelector("#footer-language-toggle")
    ].filter(Boolean);

    const translations = {
        es: {
            languageButton: "ENGLISH",
            navHome: "Inicio",
            navShop: "Tienda",
            navUpload: "Upload",
            navAbout: "Conozcámonos",
            navCart: "Carrito",
            navAccount: "Cuenta",
            navQuestions: "Consultas",
            navQuote: "Cotizar por WhatsApp"
        },

        en: {
            languageButton: "ESPAÑOL",
            navHome: "Home",
            navShop: "Shop",
            navUpload: "Upload",
            navAbout: "About us",
            navCart: "Cart",
            navAccount: "Account",
            navQuestions: "Questions",
            navQuote: "Get a quote on WhatsApp"
        }
    };

    let currentLanguage =
        localStorage.getItem("inksticker-language") || "es";

    function applyLanguage(language) {

        currentLanguage = language;

        document.documentElement.lang = language;

        localStorage.setItem("inksticker-language", language);

        document.querySelectorAll("[data-translate]").forEach(element => {

            const key = element.getAttribute("data-translate");

            if (translations[language][key]) {
                element.textContent = translations[language][key];
            }

        });

        document.querySelectorAll("[data-translate-placeholder]").forEach(element => {

            const key = element.getAttribute("data-translate-placeholder");

            if (translations[language][key]) {
                element.placeholder = translations[language][key];
            }

        });

        languageButtons.forEach(button => {
            button.textContent = translations[language].languageButton;
        });
    }

    languageButtons.forEach(button => {

        button.addEventListener("click", () => {

            const newLanguage =
                currentLanguage === "es" ? "en" : "es";

            applyLanguage(newLanguage);

        });

    });

    applyLanguage(currentLanguage);

});
