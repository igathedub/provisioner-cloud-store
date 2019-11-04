import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAllocatedGroupRange } from 'app/shared/model/allocated-group-range.model';

type EntityResponseType = HttpResponse<IAllocatedGroupRange>;
type EntityArrayResponseType = HttpResponse<IAllocatedGroupRange[]>;

@Injectable({ providedIn: 'root' })
export class AllocatedGroupRangeService {
  public resourceUrl = SERVER_API_URL + 'api/allocated-group-ranges';

  constructor(protected http: HttpClient) {}

  create(allocatedGroupRange: IAllocatedGroupRange): Observable<EntityResponseType> {
    return this.http.post<IAllocatedGroupRange>(this.resourceUrl, allocatedGroupRange, { observe: 'response' });
  }

  update(allocatedGroupRange: IAllocatedGroupRange): Observable<EntityResponseType> {
    return this.http.put<IAllocatedGroupRange>(this.resourceUrl, allocatedGroupRange, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAllocatedGroupRange>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAllocatedGroupRange[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
