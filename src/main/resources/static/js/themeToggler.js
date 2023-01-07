
function initThemeSelector() {
    const themeToggler = document.querySelector(".theme-toggler");
    const themeStylesheetLink = document.getElementById("themeStylesheetLink");
    const currentTheme = localStorage.getItem("theme") || "default_theme";

    function activateTheme(themeName) {
        themeStylesheetLink.setAttribute("href", `/css/${themeName}.css`);
    }

    themeToggler.addEventListener('click', () => {
        let oldTheme = localStorage.getItem("theme") || "default_theme";
        if (oldTheme === "default_theme") {
            activateTheme("dark_theme");
            localStorage.setItem("theme", "dark_theme");
        }
        else if(oldTheme === "dark_theme"){
            activateTheme("default_theme");
            localStorage.setItem("theme", "default_theme");
        }

        themeToggler.querySelector('span:nth-child(1)').classList.toggle('active');
        themeToggler.querySelector('span:nth-child(2)').classList.toggle('active');
    })

    activateTheme(currentTheme);
}

initThemeSelector();