/*
 * This javascript function forces the filmlist, in FilmResults.jsp, 
 * to be sorted on load according to "filmsorter.js".  Otherwise, the
 * list would be sorted in database order.
 */

function setDefaultFilmOrder()
{
  sortFilmList('title', 'filmol');
}

window.onload=setDefaultFilmOrder;