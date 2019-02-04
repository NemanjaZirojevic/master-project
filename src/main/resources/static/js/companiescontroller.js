var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
var table=null;
$(document).ready(function ()
{
    if (table == null)
    {
        table = $('#companieDetailsTable').DataTable({
            responsive: true,
            autoWidth: false,

            ajax:
                {
                    "url": "rest/stocks/findAll",
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
                {data: "id"},
                {data: "name"},
                {
                    data: null,
                    className: "center",
                    render: function (data, type, row) {


                            return '<button id="deactivate" href="" class=" btn btn-danger" type="button">Obriši</button>';


                    }

                },

            ],
        });
    }
    else {

        table.ajax.reload();
    }

    $('#companieDetailsTable tbody').on( 'click', 'button', function ()
    {
        var data = table.row( $(this).parents('tr') ).data();
        var id = $(this).attr('id');

        if(id=='deactivate')
        {
            swal({
                title: 'Da li ste sigurni da želite da obrišete kompaniju?',
                text: "Potvrdom operacije, \n izabrana kompanija neće biti prisutna na berzi",
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
                    var price = data.price;
                    var stock = {};
                    stock.id=id;
                    stock.name=name;
                    stock.price = price;
                    console.log(JSON.stringify(stock))


                    $.ajax({
                        url: "rest/stocks/delete/stock",
                        type: "DELETE",
                        beforeSend : function (xhr) {
                            $("#spinner").css('visibility','visible');
                            xhr.setRequestHeader(header, token);
                        },
                        contentType: "application/json",
                        data: JSON.stringify(stock),
                        complete: function (response) {


                            if(response.status == 200) {

                                $("#spinner").css('visibility','hidden');
                                swal({
                                    title: 'Izmene su uspešno sačuvane!',
                                    text: "Uspešno ste izbrisali izabranu kompaniju!",
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
                                    text: "Desila se greška prilikom brisanja kompanije, \n molimo pokušajte ponovo!",
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


// add new company
$("#addCompanyModal").on('click',function () {
    $('#addStockModal').modal();
});
$("#addCompany").on('click',function ()
{

    var name = $("#newCompanyName").val().trim();
    var id = $("#newCompanyId").val().trim();



    if(name=='' || name==null)
    {
        swal({
            title: 'Niste uneli naziv kompanije!',
            text: "Molimo vas da unesete naziv kompanije, \n pa pokušajte ponovo",
            type: 'warning',
            allowOutsideClick: false,
            confirmButtonText: 'Ok',
            confirmButtonColor: '#3085d6',
            buttonsStyling: true,
            confirmButtonClass: 'btn btn-primary',
        });

        return;

    }
    if(id =='' || id ==null)
    {

        swal({
            title: 'Niste uneli id kompanije!',
            text: "Molimo vas da unesete id kompanije, \n pa pokušajte ponovo",
            type: 'warning',
            allowOutsideClick: false,
            confirmButtonText: 'Ok',
            confirmButtonColor: '#3085d6',
            buttonsStyling: true,
            confirmButtonClass: 'btn btn-primary',
        });

        return;
    }




    var stock = {};
    stock.id = id;
    stock.name = name;

    console.log(stock);


    $.ajax({
        url: "rest/stocks/add/stock",
        type: "POST",
        beforeSend : function (xhr) {
            $("#spinner").css('visibility','visible');
            xhr.setRequestHeader(header, token);
        },
        contentType: "application/json",
        data: JSON.stringify(stock),
        success: function (responseCode)
        {
            console.log(responseCode);

            if(responseCode==1) {
                $("#spinner").css('visibility', 'hidden');
                swal({
                    title: 'Izmene su uspešno sacuvane!',
                    text: "Uspešno ste dodali novu kompaniju!",
                    type: 'success',
                    allowOutsideClick: false,
                    showCancelButton: false,
                    confirmButtonColor: '#3085d6',
                    confirmButtonText: 'Potvrdi',
                    confirmButtonClass: 'btn btn-success',
                    buttonsStyling: true,
                    reverseButtons: true
                });

                $("#newCompanyName").val("");
                $("#newCompanyId").val("");
                $("#addStockModal").modal('toggle');
            }else {
                $("#spinner").css('visibility', 'hidden');
                swal({
                    title: 'Greska!',
                    text: "Kompanija sa unetim ID-jem vec postoji!",
                    type: 'error',
                    allowOutsideClick: false,
                    showCancelButton: false,
                    confirmButtonColor: '#3085d6',
                    confirmButtonText: 'Potvrdi',
                    confirmButtonClass: 'btn btn-danger',
                    buttonsStyling: true,
                    reverseButtons: true
                });
                $("#newCompanyName").val("");
                $("#newCompanyId").val("");
                $("#addStockModal").modal('toggle');
            }


            if(table!=null) {
                table.ajax.reload();
            }


        },
        error: function (response)
        {
            $("#spinner").css('visibility','hidden');
            swal({
                title: 'Greška!',
                text: "Desila se greška prilikom dodavanja kompanije, \n molimo pokušajte ponovo!",
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