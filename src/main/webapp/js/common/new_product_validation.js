$(function () {

    $("#productNameForm").validate({
        rules: {
            productName: {
                required: true,
                pattern: /^[a-zA-Z]+$/
            },
        },
        messages: {
            productName: {
                pattern: "Use only letters",
                required: "This field is required",
            },
        }
    })

    $("#categoryNameForm").validate({
        rules: {
            category: {
                required: true,
                pattern: /^[a-zA-Z]+$/
            },
        },
        messages: {
            category: {
                pattern: "Choose category",
                required: "This field is required",
            },
        }
    })

    $("#newCategoryForm").validate({

        rules: {
            name: "required",
            newCategoryName: {
                required: "#newCategory:visible",
                pattern: /^[a-zA-Z]+$/
            },
        },
        messages: {
            newCategoryName: {
                pattern: "Use only letters",
                required: "This field is required",
            },
        }
    })

    $("#changeCategoryForm").validate({

        rules: {
            name: "required",
            newCategoryName: {
                required: "#newCategory:visible",
                pattern: /^[a-zA-Z]+$/
            },
        },
        messages: {
            newCategoryName: {
                pattern: "Use only letters",
                required: "This field is required",
            },
        }
    })


    $("#middleForm").validate({

        rules: {
            gender: {
                required: true,
                pattern: /^[1,2].*/
            },
            age: {
                required: true,
                pattern: /^[1-9]*$/,
            },
            weight: {
                required: true,
                pattern: /^[1-9]*$/,
            },
            price: {
                required: true,
                pattern: /^[1-9]*$/,
            },
            count: {
                required: true,
                pattern: /^[1-9]*$/,
            },
            lifespan: {
                required: true,
                pattern: /^[a-zA-Z0-9_ ]*$/,
            },

        },
        messages: {
            gender: {
                pattern: "Choose category",
                required: "This field is required",
            },
            age: {
                pattern: "Invalid number",
                required: "This field is required",
            },
            weight: {
                pattern: "Invalid number",
                required: "This field is required",
            },
            price: {
                pattern: "Invalid number",
                required: "This field is required",
            },
            count: {
                pattern: "Invalid number",
                required: "This field is required",
            },
            lifespan: {
                pattern: "Use letters and numbers",
                required: "This field is required",
            },
        }
    })

})