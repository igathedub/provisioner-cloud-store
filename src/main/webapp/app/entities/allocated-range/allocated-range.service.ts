import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAllocatedRange } from 'app/shared/model/allocated-range.model';

type EntityResponseType = HttpResponse<IAllocatedRange>;
type EntityArrayResponseType = HttpResponse<IAllocatedRange[]>;

@Injectable({ providedIn: 'root' })
export class AllocatedRangeService {
  public resourceUrl = SERVER_API_URL + 'api/allocated-ranges';

  constructor(protected http: HttpClient) {}

  create(allocatedRange: IAllocatedRange): Observable<EntityResponseType> {
    return this.http.post<IAllocatedRange>(this.resourceUrl, allocatedRange, { observe: 'response' });
  }

  update(allocatedRange: IAllocatedRange): Observable<EntityResponseType> {
    return this.http.put<IAllocatedRange>(this.resourceUrl, allocatedRange, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAllocatedRange>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAllocatedRange[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
