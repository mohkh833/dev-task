/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { MovieRequest } from '../../models/movie-request';
import { MovieResponse } from '../../models/movie-response';

export interface SaveMovie$Params {
      body: MovieRequest
}

export function saveMovie(http: HttpClient, rootUrl: string, params: SaveMovie$Params, context?: HttpContext): Observable<StrictHttpResponse<MovieResponse>> {
  const rb = new RequestBuilder(rootUrl, saveMovie.PATH, 'post');
  if (params) {
    rb.body(params.body, 'application/json');
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<MovieResponse>;
    })
  );
}

saveMovie.PATH = '/movies';
