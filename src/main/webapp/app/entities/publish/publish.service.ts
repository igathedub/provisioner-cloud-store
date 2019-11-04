import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPublish } from 'app/shared/model/publish.model';

type EntityResponseType = HttpResponse<IPublish>;
type EntityArrayResponseType = HttpResponse<IPublish[]>;

@Injectable({ providedIn: 'root' })
export class PublishService {
  public resourceUrl = SERVER_API_URL + 'api/publishes';

  constructor(protected http: HttpClient) {}

  create(publish: IPublish): Observable<EntityResponseType> {
    return this.http.post<IPublish>(this.resourceUrl, publish, { observe: 'response' });
  }

  update(publish: IPublish): Observable<EntityResponseType> {
    return this.http.put<IPublish>(this.resourceUrl, publish, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPublish>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPublish[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
