import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMeshGroup } from 'app/shared/model/mesh-group.model';

type EntityResponseType = HttpResponse<IMeshGroup>;
type EntityArrayResponseType = HttpResponse<IMeshGroup[]>;

@Injectable({ providedIn: 'root' })
export class MeshGroupService {
  public resourceUrl = SERVER_API_URL + 'api/mesh-groups';

  constructor(protected http: HttpClient) {}

  create(meshGroup: IMeshGroup): Observable<EntityResponseType> {
    return this.http.post<IMeshGroup>(this.resourceUrl, meshGroup, { observe: 'response' });
  }

  update(meshGroup: IMeshGroup): Observable<EntityResponseType> {
    return this.http.put<IMeshGroup>(this.resourceUrl, meshGroup, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMeshGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMeshGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
