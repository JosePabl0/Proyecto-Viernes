document.addEventListener("DOMContentLoaded", () => {
    const hero = document.querySelector("#banner");
    const slides = Array.from(document.querySelectorAll("#banner .hero-slide"));
    const dots = Array.from(document.querySelectorAll("#banner .hero-dot"));
    const previousButton = document.querySelector("#banner .hero-prev");
    const nextButton = document.querySelector("#banner .hero-next");

    if (!hero || slides.length === 0) {
        return;
    }

    let currentIndex = 0;
    let autoplayId = null;

    function showSlide(index) {
        currentIndex = (index + slides.length) % slides.length;

        slides.forEach((slide, i) => {
            const active = i === currentIndex;

            slide.classList.toggle("is-active", active);
            slide.setAttribute("aria-hidden", String(!active));
        });

        dots.forEach((dot, i) => {
            const active = i === currentIndex;

            dot.classList.toggle("is-active", active);
            dot.setAttribute("aria-pressed", String(active));
        });
    }

    function stopAutoplay() {
        if (autoplayId !== null) {
            window.clearInterval(autoplayId);
            autoplayId = null;
        }
    }

    function startAutoplay() {
        stopAutoplay();

        autoplayId = window.setInterval(() => {
            showSlide(currentIndex + 1);
        }, 5000);
    }

    previousButton?.addEventListener("click", () => {
        showSlide(currentIndex - 1);
        startAutoplay();
    });

    nextButton?.addEventListener("click", () => {
        showSlide(currentIndex + 1);
        startAutoplay();
    });

    dots.forEach((dot, index) => {
        dot.addEventListener("click", () => {
            showSlide(index);
            startAutoplay();
        });
    });

    document.addEventListener("visibilitychange", () => {
        if (document.hidden) {
            stopAutoplay();
        } else {
            startAutoplay();
        }
    });

    showSlide(0);
    startAutoplay();
});
