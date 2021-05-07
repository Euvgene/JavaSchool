$(function () {
    $("#form").validate({
        rules: {
            firstname: {
                required: true,
                minlength: 4,
                maxlength: 16,
                pattern: /^[a-zA-Z0-9]+$/
            },

            lastname: {
                required: true,
                pattern: /^[a-zA-Z]+$/,
                maxlength: 10,
            },
            password: {
                required: true,
                pattern: /^[a-zA-Z0-9]+$/,
                minlength: 8,
            },
            password1: {
                required: true,
                pattern: /^[a-zA-Z0-9]+$/,
                rangelength: [8, 20],
            },
            password2: {
                required: true,
                pattern: /^[a-zA-Z0-9]+$/,
                rangelength: [8, 20],
            },
            birthday: {
                required: true,
            },
            email: {
                required: true,
                pattern: /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/,
            },
            street: {
                required: true,
            },
            house: {
                required: true,
                pattern: /^[1-9]*$/,
            },
            flat: {
                required: true,
                pattern: /^[1-9]*$/,
            },
            country: {
                required: true,
            },
            city: {
                required: true,
            },
            zip: {
                required: true,
                pattern: /^[0-9]*$/,
            },
        },
        messages: {
            firstname: {
                pattern: "Use letters and numbers",
                required: "This field is required",
                minlength: "At least 4 characters",
                maxlength: "Less than 10 characters",

            },
            lastname: {
                pattern: "Use only letters",
                required: "This field is required",
                minlength: "At least 4 characters",
                maxlength: "Less than 10 characters",
            },
            password: {
                pattern: "Use only letters or numbers",
                required: "This field is required",
                minlength: "Higher than 8 characters",
            },
            password1: {
                pattern: "Use only letters or numbers",
                required: "This field is required",
                rangelength: "Range 8-20 characters",
            },
            password2: {
                pattern: "Use only letters or numbers",
                required: "This field is required",
                rangelength: "Range 8-20 characters",
            },
            birthday: {
                required: "This field is required",
            },
            email: {
                required: "This field is required",
            },
            street: {
                required: "This field is required",
            },
            house: {
                required: "This field is required",
            },
            flat: {
                required: "This field is required",
            },
            country: {
                required: "This field is required",
            },
            city: {
                required: "This field is required",
            },
            zip: {
                required: "This field is required",
            },
        }
    });
})