var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
var table=null;
$(document).ready(function ()
{
    if (table == null)
        {

            table = $('#userDetailsTable').DataTable({
                responsive: true,
                autoWidth: false,

                ajax:
                    {
                        "url": "/rest/findAllUsers",
                        "type": "GET",
                        "contentType": "application/json",
                        "beforeSend": function (xhr) {
                            xhr.setRequestHeader(header, token);
                            $("#spinner").css('visibility', 'visible');
                        },
                        "complete": function (response) {
                            $("#spinner").css('visibility', 'hidden');

                        }

                    },

                columns: [
                    {data: "name"},
                    {data: "lastname"},
                    {data: "email"},
                    {data: "password",
                        "visible":false
                    },

                    {
                        data: null,
                        className: "center",
                        render: function (data, type, row) {

                            if (row.enabled == false) {
                                return '<button href="" id="activate" class=" btn btn-success" type="button">Aktiviraj</button> <button id="change" href="" class="btn btn-warning" type="button">Izmeni</button>';
                            }

                            else {

                                return '<button id="deactivate" href="" class=" btn btn-danger" type="button">Obriši</button> <button id="change" href="" class="btn btn-warning" type="button">Izmeni</button>';

                            }
                        }

                    },

                ],
            });
        }
        else {

            table.ajax.reload();
        }

    $('#userDetailsTable tbody').on( 'click', 'button', function ()
    {
        var data = table.row( $(this).parents('tr') ).data();
        var id = $(this).attr('id');
        console.log(data);

        if(id=='change')
        {

            $("#userNameInput").val(data.name);
            $("#userLastNameInput").val(data.lastname);
            $("#userEmailInput").val(data.email);
            $("#userId").val(data.id);
            $("#authorities").val(data.password);




            if(data.enabled==true)
            {
                $("#userStatusInput").val("Aktivan");

            }
            else
            {
                $("#userStatusInput").val("Nije aktivan");

            }

            $('#privilegeChange').modal();
        }
        if(id=='deactivate')
        {
            swal({
                title: 'Da li ste sigurni da želite da obrišete korisnika?',
                text: "Potvrdom operacije, \n izabrani korisnik neće imati pristup berzi",
                type: 'warning',
                allowOutsideClick: false,
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Potvrdi',
                cancelButtonText: 'Odustani',
                confirmButtonClass: 'btn btn-primary',
                cancelButtonClass: 'btn btn-danger',
                buttonsStyling: true,
                reverseButtons: true
            }).then(function (isConfirm)

            {
                var confirm=isConfirm['value'];
                if (confirm==true)
                {
                    var id = data.id;
                    var name = data.name;
                    var lastname = data.lastname;
                    var email = data.email;
                    var password = data.password;
                    var user = {};
                    user.id=id;
                    user.name=name;
                    user.lastname=lastname;
                    user.email=email;
                    user.password=password;


                    $.ajax({
                        url: "rest/deleteUser",
                        type: "DELETE",
                        beforeSend : function (xhr) {
                            $("#spinner").css('visibility','visible');
                            xhr.setRequestHeader(header, token);
                        },
                        contentType: "application/json",
                        data: JSON.stringify(user),
                        complete: function (response) {


                            if(response.status == 200) {

                                $("#spinner").css('visibility','hidden');
                                swal({
                                    title: 'Izmene su uspešno sačuvane!',
                                    text: "Uspešno ste izbrisali izabranog korisnika!",
                                    type: 'success',
                                    showCancelButton: false,
                                    confirmButtonColor: '#3085d6',
                                    confirmButtonText: 'Potvrdi',
                                    confirmButtonClass: 'btn btn-success',
                                    buttonsStyling: false,
                                    reverseButtons: true
                                });
                                table.ajax.reload();

                            }
                            else
                            {
                                $("#spinner").css('visibility','hidden');
                                swal({
                                    title: 'Greška!',
                                    text: "Desila se greška prilikom brisanja korisnika, \n molimo pokušajte ponovo!",
                                    type: 'error',
                                    allowOutsideClick: false,
                                    showCancelButton: false,
                                    confirmButtonColor: '#3085d6',
                                    confirmButtonText: 'Potvrdi',
                                    confirmButtonClass: 'btn btn-primary',
                                    buttonsStyling: false,
                                    reverseButtons: true
                                });


                            }

                        },

                    })

                } else
                {


                }
            })
        }

        else if(id=="activate")
        {
            swal({
                title: 'Da li ste sigurni da želite da aktivirate korisnika?',
                text: "Potvrdom  operacije, \n izabrani korisnik će imati pristup portalu",
                type: 'warning',
                allowOutsideClick: false,
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Potvrdi',
                cancelButtonText: 'Odustani',
                confirmButtonClass: 'btn btn-primary',
                cancelButtonClass: 'btn btn-danger',
                buttonsStyling: true,
                reverseButtons: true
            }).then(function (isConfirm)

            {
                var confirm=isConfirm['value'];
                if (confirm==true)
                {
                    var adminId = data.administratorId;
                    var adminNameAndLastName = data.administratorFullNameAndLastName;
                    var adminName = data.administratorName;
                    var adminPassword = data.password;
                    var adminStatus = 1 ;
                    var adminPrivilege = data.groupId;



                    var administrator = new Object(null);
                    administrator.administratorId = adminId;
                    administrator.administratorFullNameAndLastName = adminNameAndLastName;
                    administrator.administratorName = adminName;
                    administrator.password = adminPassword;
                    administrator.groupId = adminPrivilege;
                    administrator.status = adminStatus;


                } else
                {


                }
            })

        }

    });
});

//update user creditals
$("#saveChanges").on('click',function () {

    var id = $("#userId").val();
    var name = $("#userNameInput").val();
    var lastname = $("#userLastNameInput").val();
    var email = $("#userEmailInput").val();
    var password = $("#authorities").val();
    var user = {};
    user.id=id;
    user.name=name;
    user.lastname=lastname;
    user.email=email;
    user.password=password;

    $.ajax({

        url:"rest/updateUsers",
        type: "POST",
        beforeSend : function (xhr) {
            $("#spinner").css('visibility','visible');
            xhr.setRequestHeader(header, token);
        },
        data:JSON.stringify(user),
        contentType: "application/json",
        success: function (response) {
            console.log(response);
            $("#spinner").css('visibility','hidden');
            $('#privilegeChange').modal('toggle');

            swal({
                title: 'Izmene su uspešno sačuvane!',
                text: "Uspešno ste sačuvali izmene za izabranog korisnika!",
                type: 'success',
                showCancelButton: false,
                confirmButtonColor: '#3085d6',
                confirmButtonText: 'Potvrdi',
                confirmButtonClass: 'btn btn-success',
                buttonsStyling: false,
                reverseButtons: true
            });
            table.ajax.reload();
        },
        error: function (response) {
            $("#spinner").css('visibility','hidden');
        }

    })
});

// add new user
$("#openAddingModal").on('click',function () {
    $('#addUserModal').modal();
});
$("#addUser").on('click',function ()
{

    var firstName = $("#newUserFirstName").val().trim();
    var lastName = $("#newUserLastName").val().trim();
    var password = $("#newUserPassword").val().trim();
    var confirmPassword = $("#newUserConfirmPassword").val().trim();
    var email = $("#newUserEmail").val().trim();
    var confirmEmail = $("#newUserConfirmEmail").val().trim();
    var terms = $("#terms").val();


    if(password!=confirmPassword || confirmPassword=='' || confirmPassword==null)
{
    swal({
        title: 'Lozinke se ne poklapaju!',
        text: "Molimo vas da ponovo unesete korisničku lozinku, \n pa pokušajte ponovo",
        type: 'warning',
        allowOutsideClick: false,
        confirmButtonText: 'Ok',
        confirmButtonColor: '#3085d6',
        buttonsStyling: true,
        confirmButtonClass: 'btn btn-primary',
    });

    return;
}

    if(email!=confirmEmail || confirmEmail=='' || confirmEmail==null)
    {
        swal({
            title: 'Email-ovi se ne poklapaju!',
            text: "Molimo vas da ponovo unesete email za korisnika, \n pa pokušajte ponovo",
            type: 'warning',
            allowOutsideClick: false,
            confirmButtonText: 'Ok',
            confirmButtonColor: '#3085d6',
            buttonsStyling: true,
            confirmButtonClass: 'btn btn-primary',
        });

        return;
    }
    if(firstName=='' || firstName==null)
    {
        swal({
            title: 'Niste uneli ime korisnika!',
            text: "Molimo vas da unesete ime korisnika, \n pa pokušajte ponovo",
            type: 'warning',
            allowOutsideClick: false,
            confirmButtonText: 'Ok',
            confirmButtonColor: '#3085d6',
            buttonsStyling: true,
            confirmButtonClass: 'btn btn-primary',
        });

        return;

    }
    if(lastName =='' || lastName ==null)
    {

        swal({
            title: 'Niste uneli prezime korisnika!',
            text: "Molimo vas da unesete prezime korisnika, \n pa pokušajte ponovo",
            type: 'warning',
            allowOutsideClick: false,
            confirmButtonText: 'Ok',
            confirmButtonColor: '#3085d6',
            buttonsStyling: true,
            confirmButtonClass: 'btn btn-primary',
        });

        return;
    }
    if(email=='' || email==null)
    {
        swal({
            title: 'Niste uneli email korisnika!',
            text: "Molimo vas da unesete email korisnika, \n pa pokušajte ponovo",
            type: 'warning',
            allowOutsideClick: false,
            confirmButtonText: 'Ok',
            confirmButtonColor: '#3085d6',
            buttonsStyling: true,
            confirmButtonClass: 'btn btn-primary',
        });

        return;
    }



    var userRegistrationDto = {};
    userRegistrationDto.firstName = firstName;
    userRegistrationDto.lastName = lastName;
    userRegistrationDto.password = password;
    userRegistrationDto.confirmPassword = confirmPassword;
    userRegistrationDto.email = email;
    userRegistrationDto.confirmEmail = confirmEmail;
    if(terms=='on')
    userRegistrationDto.terms = true;
    else
        userRegistrationDto.terms = false;



    $.ajax({
        url: "rest/addUser",
        type: "POST",
        beforeSend : function (xhr) {
            $("#spinner").css('visibility','visible');
            xhr.setRequestHeader(header, token);
        },
        contentType: "application/json",
        data: JSON.stringify(userRegistrationDto),
        success: function (responseCode)
        {
                $("#spinner").css('visibility','hidden');
                swal({
                    title: 'Izmene su uspešno sacuvane!',
                    text: "Uspešno ste dodali novog korisnika!",
                    type: 'success',
                    allowOutsideClick: false,
                    showCancelButton: false,
                    confirmButtonColor: '#3085d6',
                    confirmButtonText: 'Potvrdi',
                    confirmButtonClass: 'btn btn-success',
                    buttonsStyling: true,
                    reverseButtons: true
                });

                $("#newUserFirstName").val("");
                $("#newUserLastName").val("");
                $("#newUserPassword").val("");
                $("#newUserConfirmPassword").val("");
                $("#newUserEmail").val("");
                $("#newUserConfirmEmail").val("");
                $("#addUserModal").modal('toggle');



                if(table!=null) {
                    table.ajax.reload();
                }


        },
        error: function (response)
        {
            $("#spinner").css('visibility','hidden');
            swal({
                title: 'Greška!',
                text: "Desila se greška prilikom dodavanja korisnika, \n molimo pokušajte ponovo!",
                type: 'error',
                allowOutsideClick: false,
                showCancelButton: false,
                confirmButtonColor: '#3085d6',
                confirmButtonText: 'Potvrdi',
                confirmButtonClass: 'btn btn-primary',
                buttonsStyling: true,
                reverseButtons: true
            });
        }

    })

})