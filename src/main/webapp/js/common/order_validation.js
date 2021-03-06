$(function () {
    $("#addressForm").validate({

        rules: {
            name: "required",
            address: {
                required: "#orderAddress:visible",
                pattern: /^[-.,;:a-zA-Z0-9_ ]*$/
            },
        },
        messages: {
            address: {
                pattern: "Use letters,numbers. \".-,:;\"",
                required: "This field is required",
            },
        }
    });

    $("#changeOrderForm").validate({

        rules: {
            address: {
                required: true,
                pattern: /^[-.,;:a-zA-Z0-9_ ]*$/
            },
        },
        messages: {
            address: {
                pattern: "Use letters,numbers. \".-,:;\"",
                required: "This field is required",
            },
        }
    });

    $("#paymentForm").validate({

        rules: {
            name: "required",
            cardname: {
                required: "#cc-name:visible",
                pattern: /^[a-zA-Z_ ]*$/,
            },
            number: {
                required: "#cc-number:visible",
                pattern: /^[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}$/,
            },
            expiration: {
                required: "#cc-expiration:visible",
                pattern:/^(0[1-9]|1[0-2])\/?([0-9]{2})$/,

            },
            cvv: {
                required: "#cc-cvv:visible",
                pattern:/^[0-9]{3}$/,
            },
        },
        messages: {
            name: {
                pattern: "Use only letters",
                required: "This field is required",
            },
            number: {
                pattern: "invalid number",
                required: "This field is required",
            },
            expiration: {
                pattern: "Use only letters or numbers",
                required: "This field is required",

            },
            cvv: {
                pattern: "Use only letters or numbers",
                required: "This field is required",
                rangelength: "Range 8-20 characters",
            },
        }

    })

})