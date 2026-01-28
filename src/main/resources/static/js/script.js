async function setPersonsOnPage(step) {
    let response = await fetch(`/home/pages/${step}`, {method: 'POST'});
    let text = await response.text();
    if (text.startsWith("Changed")) {
        window.location.href = "/home/people?page=1";
    }
}

function approveRemove(id) {
    return confirm("Are you sure?");
}

async function deleteByRow(id) {
    if (!approveRemove()) {
        return;
    }

    let response = await fetch(`/home/delete/${id}`, {method: 'DELETE'});
    let href = await response.text();
    window.location.href = href;
}

let sortMulti = 1;

let sortStates = {};

function sortTable(index, num) {
    let body = document.getElementsByTagName('tbody')[0];
    let rows = Array.from(body.getElementsByTagName('tr'));
    let headers = document.querySelectorAll('th');
    console.log(rows)

    if (sortStates[index] === undefined) sortStates[index] = 'asc'

    sortMulti = sortStates[index] === 'asc' ? 1 : -1;

    let sortedRows = rows.sort((rowA, rowB) => {
        let cellA = rowA.cells[index].innerText.toLowerCase();
        let cellB = rowB.cells[index].innerText.toLowerCase();

        if (!isNaN(cellA) && !isNaN(cellB))
            return sortMulti * (Number(cellA) - Number(cellB));

        return sortMulti * (cellA.localeCompare(cellB));

    });


    for (const header of headers) {
        header.innerText = header.innerText.split(' ')[0];
    }

    sortedRows.forEach(row => {
        let numElem = row.querySelector('td');
        console.log(numElem.innerText);
        numElem.innerText = ++num;
        body.appendChild(row);
    });

    if (sortStates[index] === 'asc') {
        headers[index].innerText += ' ⬆'
        sortStates[index] = 'desc'
    } else {
        headers[index].innerText += ' ⬇'
        sortStates[index] = 'asc'
    }
}

function filterTable() {
    let input = document.querySelector('#globalFilter');
    let value = input.value.toLowerCase();

    let selectedField = document.querySelector('input[name="searchField"]:checked').value;
    window.location.href = `/home/filter?field=${selectedField}&value=${value}`;
}