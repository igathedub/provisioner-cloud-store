import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IElement } from 'app/shared/model/element.model';

type EntityResponseType = HttpResponse<IElement>;
type EntityArrayResponseType = HttpResponse<IElement[]>;

@Injectable({ providedIn: 'root' })
export class ElementService {
  public resourceUrl = SERVER_API_URL + 'api/elements';

  constructor(protected http: HttpClient) {}

  create(element: IElement): Observable<EntityResponseType> {
    return this.http.post<IElement>(this.resourceUrl, element, { observe: 'response' });
  }

  update(element: IElement): Observable<EntityResponseType> {
    return this.http.put<IElement>(this.resourceUrl, element, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IElement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IElement[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
